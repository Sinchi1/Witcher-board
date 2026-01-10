package org.kursach.dto;

import jakarta.validation.constraints.Size;

public record RegisterRequest (
        @Size(min = 3, max = 25, message = "Username must contain 3 to 25 symbols")
        String username,
        @Size(min = 3, max = 25, message = "Password must contain 3 to 25 symbols")
        String password
) {
}
