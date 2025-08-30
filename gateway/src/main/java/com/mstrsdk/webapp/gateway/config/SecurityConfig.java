package com.mstrsdk.webapp.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ServerWebExchange;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    @Order(-1)
    public GlobalFilter globalFilter() {
        return (exchange, chain) ->
                exchange.getPrincipal()
                        .cast(Authentication.class)
                        .flatMap(auth -> {
                            final ServerHttpRequest request = exchange
                                    .getRequest()
                                    .mutate()
                                    .header("X-User-Name", auth.getName())
                                    .build();

                            final ServerWebExchange mutatedExchange = exchange
                                    .mutate()
                                    .request(request)
                                    .build();

                            return chain.filter(mutatedExchange);
                        })
                        .switchIfEmpty(chain.filter(exchange));
    }

}
