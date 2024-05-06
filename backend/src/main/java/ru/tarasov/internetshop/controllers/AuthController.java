package ru.tarasov.internetshop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final PersonDetailsService personDetailsService;

    private final RegistrationService registrationService;

    private final ModelMapper modelMapper;

    private final JWTUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;
    @Autowired
    public AuthController(PersonDetailsService personDetailsService, RegistrationService registrationService, ModelMapper modelMapper, JWTUtil jwtUtil, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.personDetailsService = personDetailsService;
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @Operation(summary = "Регистрация пользователя", tags = {"add"})
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody PersonDTO personDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity<>("Ошибка при регистрации", HttpStatus.NOT_FOUND);

        try {
            registrationService.register(converToPerson(personDTO));
            String token = jwtUtil.generateToken(personDTO.getName());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Ошибка при регистрации", HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "Аутентификация пользователя", tags = {"login"})
    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        return ResponseEntity.ok(registrationService.login(authenticationDTO));
    }


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

    @PostMapping("/logout")
    public void logout(@RequestBody RefreshTokenRequest request){
        refreshTokenService.deleteByToken(request.getRefreshToken());
    }


    public Person converToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }
}
