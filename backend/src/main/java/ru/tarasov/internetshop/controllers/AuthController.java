package ru.tarasov.internetshop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tarasov.internetshop.dto.AuthenticationDTO;
import ru.tarasov.internetshop.dto.PersonDTO;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.security.JWTUtil;
import ru.tarasov.internetshop.security.PersonDetails;
import ru.tarasov.internetshop.services.PersonDetailsService;
import ru.tarasov.internetshop.services.RegistrationService;

import java.util.Map;

@RestController
public class AuthController {

    private final PersonDetailsService personDetailsService;

    private final RegistrationService registrationService;

    private final ModelMapper modelMapper;

    private final JWTUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthController(PersonDetailsService personDetailsService, RegistrationService registrationService, ModelMapper modelMapper, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.personDetailsService = personDetailsService;
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Регистрация пользователя", tags = {"add"})
    @PostMapping("/registration")
    public Map<String, String> registration(@RequestBody PersonDTO personDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return Map.of("message", "oshibka!");

        registrationService.register(converToPerson(personDTO));
        String token = jwtUtil.generateToken(personDTO.getName());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        try {
            UserDetails details = personDetailsService.loadUserByUsername(authenticationDTO.getUsername());
            if (passwordEncoder.matches(authenticationDTO.getPassword(), details.getPassword())){
                return Map.of("message", "Неправильный пароль");
            }
            String token = jwtUtil.generateToken(authenticationDTO.getUsername());
            return Map.of("jwt-token", token);
        } catch (UsernameNotFoundException e){
            return Map.of("message", "Нет пользователя с таким именем");
        }
    }

    public Person converToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }
}
