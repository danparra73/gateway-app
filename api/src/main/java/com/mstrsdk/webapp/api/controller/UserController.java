package com.mstrsdk.webapp.api.controller;

import com.mstrsdk.webapp.api.dto.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public ResponseEntity<Flux<UserInfo>> getUsers(@AuthenticationPrincipal Jwt jwt) {
        logger.info("JWT: {}", jwt.getClaims().get("email"));

        final List<UserInfo> users = List.of(
                new UserInfo("U1", "Joe", "joe@mstrsdk.com"),
                new UserInfo("U2", "Karen", "karen@mstrsdk.com"),
                new UserInfo("U3", "Mario", "h=mario@mstrsdk.com"),
                new UserInfo("U4", "Aztlan", "aztlan@mstrsdk.com"),
                new UserInfo("U5", "Federico", "federico@mstrsdk.com")
        );

        return ResponseEntity.ok(Flux.fromIterable(users));
    }
}
