package ru.tarasov.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tarasov.internetshop.models.Cart;
import ru.tarasov.internetshop.models.Person;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAll();

    List<Cart> findAllByPerson(Person person);

    Object deleteById(int id);
}
