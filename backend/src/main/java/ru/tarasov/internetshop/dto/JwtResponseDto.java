package ru.tarasov.internetshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponseDto {

    private final String tokenType = "Bearer";

    private String accessToken;

    private Instant accessExpiresIn;

    private String refreshToken;

    private Instant refreshExpiresIn;
}
