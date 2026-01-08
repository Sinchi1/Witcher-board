package org.kursach.configuration;

import lombok.RequiredArgsConstructor;
import org.kursach.service.dto.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.kursach.filters.AuthFilter.auth;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
@RequiredArgsConstructor
public class NavigateRoutesConfiguration {

    public final AuthService authServiceClient;

    @Bean
    public RouterFunction<ServerResponse> gatewayRouterFunctionsPath() {
        return route("gateway")
                .route(path("/gateway/**"), http())
                .filter(lb("gateway"))
                .before(auth(authServiceClient))
                .build()
                .and(route("auth-service")
                        .route(path("/auth-service/**"), http())
                        .filter(lb("auth-service"))
                        .build())
                .and(route("notification-service")
                        .route(path("/notification-service/**"), http())
                        .filter(lb("notification-service"))
                        .before(auth(authServiceClient))
                        .build())
                .and(route("monster-service")
                        .route(path("/monster-service/**"), http())
                        .filter(lb("monster-service"))
                        .before(auth(authServiceClient))
                        .build())
                .and(route("inventory-service")
                        .route(path("/inventory-service/**"), http())
                        .filter(lb("inventory-service"))
                        .before(auth(authServiceClient))
                        .build())
                .and(route("order-service")
                        .route(path("/order-service/**"), http())
                        .filter(lb("order-service"))
                        .before(auth(authServiceClient))
                        .build())
                .and(route("glossary-service")
                        .route(path("/glossary-service/**"), http())
                        .filter(lb("glossary-service"))
                        .before(auth(authServiceClient))
                        .build());
    }
}


