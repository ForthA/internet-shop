package ru.tarasov.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tarasov.internetshop.models.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByName(String name);

    Optional<Person> findPersonByNameAndPassword(String name, String password);

    boolean existsByEmail(String email);
}
