package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.tarasov.internetshop.dto.AuthenticationDTO;
import ru.tarasov.internetshop.dto.JwtResponseDto;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.models.RefreshToken;
import ru.tarasov.internetshop.repositories.PersonRepository;
import ru.tarasov.internetshop.repositories.RefreshTokenRepository;
import ru.tarasov.internetshop.repositories.RoleRepository;
import ru.tarasov.internetshop.security.JWTUtil;

import java.util.Optional;
import java.util.Set;

@Service
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final PersonService personService;

    private final PersonDetailsService personDetailsService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JWTUtil jwtUtil;

    private final RefreshTokenService refreshTokenService;

    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, PersonService personService, PersonDetailsService personDetailsService, RefreshTokenRepository refreshTokenRepository, JWTUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.personService = personService;
        this.personDetailsService = personDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }
    @Transactional
    public void register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRoles(Set.of(roleRepository.findByRoleName("USER").get()));
        personRepository.save(person);
    }

    public JwtResponseDto login(AuthenticationDTO authenticationDTO){
        Optional<Person> person = personService.loadUserByUsername(authenticationDTO.getUsername());
        if (person.isPresent()) {
            if (!passwordEncoder.matches(authenticationDTO.getPassword(), person.get().getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный пароль");
            }
            refreshTokenService.deleteByPersonId(person.get().getId());
            return generateResponse(person.get());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с таким именем не найден");
        }
    }

    private JwtResponseDto generateResponse(Person person){
        return new JwtResponseDto(jwtUtil.generateToken(person.getName()), refreshTokenService.createRefreshToken(person.getId()).getToken());
    }


}
