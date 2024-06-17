package ru.tarasov.internetshop.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JwtResponseDto {

    private static final String tokenType = "Bearer";

    private String accessToken;

    private String refreshToken;

    public JwtResponseDto(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
