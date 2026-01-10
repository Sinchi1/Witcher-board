package org.kursach.filters;

import org.kursach.service.ValidationToken;
import org.kursach.service.dto.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.List;
import java.util.function.Function;

public class AuthFilter {

    public static Function<ServerRequest, ServerRequest> auth(AuthService authService){
        return serverRequest -> {
            try {
                var authHeaders = serverRequest.headers().header("Authorization");
                var token = getToken(authHeaders);
                var response = authService.validate(new ValidationToken(token));
                
                String rolesHeader = String.join(",", response.roles());
                
                serverRequest = ServerRequest.from(serverRequest)
                        .header("User_id", response.userId().toString())
                        .header("Username", response.username())
                        .header("Roles", rolesHeader)
                        .build();
            } catch (ResponseStatusException e) {
                throw e;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
            }
            return serverRequest;
        };
    }

    private static String getToken(List<String> authHeaders) {
        if (authHeaders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header is missing");
        }

        var authToken = authHeaders.getFirst();

        if (authToken == null || !authToken.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header format");
        }

        var token = authToken.substring("Bearer ".length());
        return token;
    }
}
