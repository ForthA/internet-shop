package ru.tarasov.internetshop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tarasov.internetshop.dto.AuthenticationDTO;
import ru.tarasov.internetshop.dto.JwtResponseDto;
import ru.tarasov.internetshop.dto.PersonDTO;
import ru.tarasov.internetshop.exceptions.TokenRefreshException;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.models.RefreshToken;
import ru.tarasov.internetshop.models.RefreshTokenRequest;
import ru.tarasov.internetshop.security.JWTUtil;
import ru.tarasov.internetshop.services.PersonDetailsService;
import ru.tarasov.internetshop.services.RefreshTokenService;
import ru.tarasov.internetshop.services.RegistrationService;

@RestController
public class AuthController {

    private final RegistrationService registrationService;

    private final ModelMapper modelMapper;

    private final JWTUtil jwtUtil;

    private final RefreshTokenService refreshTokenService;
    @Autowired
    public AuthController(RegistrationService registrationService, ModelMapper modelMapper, JWTUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @Operation(summary = "Регистрация пользователя", tags = {"add"})
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody PersonDTO personDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity<>("Ошибка при регистрации", HttpStatus.NOT_FOUND);

        try {
            return new ResponseEntity<>(registrationService.register(converToPerson(personDTO)), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Ошибка при регистрации", HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "Аутентификация пользователя", tags = {"login"})
    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        return ResponseEntity.ok(registrationService.login(authenticationDTO));
    }

    @Operation(summary = "Получение токена по рефрештокену", tags = {"token"})
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshTokenRequest = request.getRefreshToken();

        return refreshTokenService.findByToken(refreshTokenRequest)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getPerson)
                .map(person -> {
                    String token = jwtUtil.generateToken(person.getName());
                    return ResponseEntity.ok(new JwtResponseDto(token, refreshTokenRequest));
                })
                .orElseThrow(() -> new TokenRefreshException(refreshTokenRequest, "Refresh token не в базе"));
    }

    @Operation(summary = "Логаут со странным адресом и странной работой, на вход рефрештокен", tags = {"logout"})
    @PostMapping("/my/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request){
        refreshTokenService.deleteByToken(request.getRefreshToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public Person converToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }
}
