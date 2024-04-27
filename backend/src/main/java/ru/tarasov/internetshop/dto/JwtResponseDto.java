package ru.tarasov.internetshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class JwtResponseDto {

    private final String tokenType = "Bearer";

    private String accessToken;

    private String refreshToken;

    public JwtResponseDto(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
