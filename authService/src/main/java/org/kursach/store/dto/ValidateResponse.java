package org.kursach.store.dto;

import java.util.Set;

public record ValidateResponse (
        Long userId,
        String username,
        Set<String> roles
) {
}
