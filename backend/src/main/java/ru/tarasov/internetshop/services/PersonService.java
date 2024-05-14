package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.repositories.PersonRepository;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findPersonById(int id){
        return personRepository.findById(id);
    }

    public void save(Person person){
        personRepository.save(person);
    }

    public Optional<Person> loadUserByUsername(String username){
        return personRepository.findByName(username);
    }
}
