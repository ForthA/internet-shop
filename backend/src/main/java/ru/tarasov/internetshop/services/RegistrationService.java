package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tarasov.internetshop.dto.JwtResponseDto;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.models.RefreshToken;
import ru.tarasov.internetshop.repositories.PersonRepository;
import ru.tarasov.internetshop.repositories.RoleRepository;

import java.util.List;
import java.util.Set;

@Service
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    @Transactional
    public void register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRoles(Set.of(roleRepository.findByRoleName("USER").get()));
        personRepository.save(person);
    }

    /*
    private JwtResponseDto generateResponse(Person person, RefreshToken refreshToken){

    }

     */

}
