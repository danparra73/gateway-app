package com.mstrsdk.webapp.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class SignoutController {
    final static private Logger log = LoggerFactory.getLogger(SignoutController.class);

    @Value("${webapp-uris.logout.post-logout-redirect-uri}")
    private String postLogoutRedirectUri;

    @Value("${webapp-uris.logout.idp-logout-uri}")
    private String idpLogoutUri;


    @GetMapping("signout")
    public Mono<ResponseEntity<String>> closed() {
        log.debug("signout");

        return Mono.just(ResponseEntity.ok("Session closed"));
    }

    @GetMapping("/closesession")
    public Mono<Void> logout(ServerWebExchange exchange, @AuthenticationPrincipal Authentication auth) {
        log.debug("Clear SecurityContext explicitly (in addition to invalidating the session");

        final OidcIdToken idToken = getOidcIdToken(auth);

        final URI endSessionUri = getEndSessionUri(idToken);

        final WebSessionServerSecurityContextRepository repo = new WebSessionServerSecurityContextRepository();
        return repo.save(exchange, null) // remove SecurityContext
                .then(exchange.getSession().flatMap(WebSession::invalidate)) // Invalidate session
                .then(Mono.defer(() -> {
                    exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
                    exchange.getResponse().getHeaders().setLocation(endSessionUri);
                    return exchange.getResponse().setComplete();
                }));
    }

    private URI getEndSessionUri(OidcIdToken idToken) {
        log.debug("Get End Session URI");

        final URI endSessionUri = UriComponentsBuilder
                .fromUriString(idpLogoutUri)
                .queryParam("post_logout_redirect_uri", postLogoutRedirectUri)
                .queryParam("id_token_hint", idToken.getTokenValue())
                .build()
                .toUri();

        log.debug("Final End Session URI: {}", endSessionUri);

        return endSessionUri;
    }

    private static OidcIdToken getOidcIdToken(Authentication auth) {
        log.debug("Get OIDC User");
        final OidcUser oidcUser = (OidcUser) auth.getPrincipal();

        log.debug("Get ID Token");
        final OidcIdToken idToken = oidcUser.getIdToken();

        log.debug("ID Token: {}", idToken);
        return idToken;
    }
}
