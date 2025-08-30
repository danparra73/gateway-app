package com.mstrsdk.webapp.api.controller;

import com.mstrsdk.webapp.api.dto.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/users")
    public ResponseEntity<Flux<UserInfo>> getUsers() {
        final List<UserInfo> users = List.of(
                new UserInfo("U1", "Joe", "joe@mstrsdk.com"),
                new UserInfo("U2", "Karen", "karen@mstrsdk.com"),
                new UserInfo("U3", "Mario", "h=mario@mstrsdk.com"),
                new UserInfo("U4", "Aztlan", "aztlan@mstrsdk.com")
        );

        return ResponseEntity.ok(Flux.fromIterable(users));
    }
}
