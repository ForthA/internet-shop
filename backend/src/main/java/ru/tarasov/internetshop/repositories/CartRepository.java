package ru.tarasov.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tarasov.internetshop.models.Cart;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.models.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAll();

    List<Cart> findAllByPerson(Person person);

    Object deleteById(int id);

    Optional<Cart> findCartByProductAndPerson(Product product, Person person);
}
