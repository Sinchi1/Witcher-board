package org.kursach.dto;

public record LoginRequest (
        String username,
        String password
) {
}
