package org.kursach.service.dto;

import java.util.Set;

public record TokenDTO(Long userId, String username, Set<String> roles) {
}
