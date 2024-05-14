package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tarasov.internetshop.models.Cart;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.repositories.CartRepository;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final PersonService personService;

    @Autowired
    public CartService(CartRepository cartRepository, PersonService personService) {
        this.cartRepository = cartRepository;
        this.personService = personService;
    }

    public List<Cart> findByPersonId(int userId){
        Person person = personService.findPersonById(userId).get();

        return cartRepository.findAllByPerson(person);
    }

    public List<Cart> findByPersonName(String name){
        Person person = personService.loadUserByUsername(name).get();

        return cartRepository.findAllByPerson(person);
    }

    public List<Cart> findAll(){
        return cartRepository.findAll();
    }

    public void saveCart(Cart cart){
        cartRepository.save(cart);
    }

    public void deleteCart(int id){
        cartRepository.deleteById(id);
    }
}
