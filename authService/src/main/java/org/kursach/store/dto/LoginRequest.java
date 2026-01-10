package org.kursach.store.dto;

public record LoginRequest (
        String username,
        String password
) {
}
