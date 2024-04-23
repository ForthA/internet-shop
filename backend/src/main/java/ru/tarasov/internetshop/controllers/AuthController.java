package ru.tarasov.internetshop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> registration(@RequestBody PersonDTO personDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity<>("Ошибка при регистрации", HttpStatus.NOT_FOUND);

        try {
            System.out.println(personDTO.getName() + " " + personDTO.getSurname() + " " + personDTO.getEmail() + " " + personDTO.getPassword());
            registrationService.register(converToPerson(personDTO));
            String token = jwtUtil.generateToken(personDTO.getName());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Ошибка при регистрации", HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "Аутентификация пользователя", tags = {"login"})
    @PostMapping("/login")
    public ResponseEntity<String> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        try {
            UserDetails details = personDetailsService.loadUserByUsername(authenticationDTO.getUsername());
            if (!passwordEncoder.matches(authenticationDTO.getPassword(), details.getPassword())){
                return new ResponseEntity<>("Неверный пароль", HttpStatus.UNAUTHORIZED);
            }
            String token = jwtUtil.generateToken(authenticationDTO.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (UsernameNotFoundException e){
            return new ResponseEntity<>("Нет пользователя с таким именем", HttpStatus.NOT_FOUND);
        }
    }


    public Person converToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }
}
