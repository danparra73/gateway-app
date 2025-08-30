package com.mstrsdk.webapp.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/api")
    public Mono<ResponseEntity<Map<String, String>>> apiFallback(ServerHttpRequest request) {

        MultiValueMap<String, String> queryParams = request.getQueryParams();
        final URI uri = request.getURI();

        final Map<String, String> message = new HashMap<>();
        message.put("message", "fallback");
        message.put("path", uri.getPath());
        message.put("queryParams", queryParams.toString());


        return Mono.just(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(message)
        );
    }

    @RequestMapping("/spa")
    public ResponseEntity<String> spaFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("SPA service is unavailable");
    }
}
