package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.tarasov.internetshop.models.Cart;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.models.Product;
import ru.tarasov.internetshop.repositories.CartRepository;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final PersonService personService;

    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, PersonService personService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.personService = personService;
        this.productService = productService;
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

    public void saveCart(int productId, String username){
        Person person = personService.loadUserByUsername(username).get();

        Cart cart = new Cart();
        cart.setPerson(person);
        cart.setAmount(1);
        cart.setProduct(productService.findProductById(productId));
        cartRepository.save(cart);
    }

    @Transactional
    public void addAmountCart(int productId, String username){
        Person person = personService.loadUserByUsername(username).get();
        Product product = productService.findProductById(productId);

        Cart cart = cartRepository.findCartByProductAndPerson(product, person).get();
        if (product.getAmount() < cart.getAmount() + 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Превышено количество товара на складе");
        }
        else {
            cart.setAmount(cart.getAmount() + 1);

            cartRepository.save(cart);
        }
    }

    @Transactional
    public void decreaseAmountCart(int productId, String username){
        Person person = personService.loadUserByUsername(username).get();
        Product product = productService.findProductById(productId);

        Cart cart = cartRepository.findCartByProductAndPerson(product, person).get();
        if (cart.getAmount() - 1 < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя задать отрицательно количество товара");
        }
        else if (cart.getAmount() - 1 == 0) {
            cartRepository.deleteById(cart.getId());
        }
        else {
            cart.setAmount(cart.getAmount() - 1);

            cartRepository.save(cart);
        }

    }

    public void deleteCart(int id){
        cartRepository.deleteById(id);
    }
}
