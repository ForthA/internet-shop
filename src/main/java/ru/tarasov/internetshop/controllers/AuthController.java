package ru.tarasov.internetshop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tarasov.internetshop.dto.PersonDTO;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.security.JWTUtil;
import ru.tarasov.internetshop.services.PersonService;

import java.util.Map;

@RestController
public class AuthController {

    private PersonService personService;

    private final ModelMapper modelMapper;

    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(PersonService personService, ModelMapper modelMapper, JWTUtil jwtUtil) {
        this.personService = personService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Регистрация пользователя(хз работает вроде))))", tags = {"add"})
    @PostMapping("/registration")
    public Map<String, String> registration(@RequestBody PersonDTO personDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return Map.of("message", "oshibka!");

        personService.save(converToPerson(personDTO));
        String token = jwtUtil.generateToken(personDTO.getName());
        return Map.of("jwt-token", token);
    }

    public Person converToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }
}
