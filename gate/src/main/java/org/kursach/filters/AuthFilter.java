package org.kursach.filters;

import org.kursach.service.ValidationToken;
import org.kursach.service.dto.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.function.Function;

public class AuthFilter {

    public static Function<ServerRequest, ServerRequest> auth(AuthService authService){
        return serverRequest -> {
            try {
                var authToken = serverRequest.headers().header("Authorization").getFirst();
                var token = authToken.substring("Bearer ".length());
                var response = authService.validate(new ValidationToken(token));
                serverRequest = ServerRequest.from(serverRequest)
                        .header("User_id", response.userId().toString())
                        .header("Username", response.username())
                        .header("Roles", response.roles().toArray(new String[0]))
                        .build();
            } catch (Exception ignored) {
                throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
            }
            return serverRequest;
        };
    }
}
