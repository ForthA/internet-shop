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
import ru.tarasov.internetshop.responses.CartPriceResponse;

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
        Person person = personService.findPersonById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь на найден")
        );

        return cartRepository.findAllByPerson(person);
    }

    public List<Cart> findByPersonName(String name){
        Person person = personService.loadUserByUsername(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не найден")
        );

        return cartRepository.findAllByPerson(person);
    }

    public List<Cart> findAll(){
        return cartRepository.findAll();
    }

    public void saveCart(int productId, String username){
        Person person = personService.loadUserByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "При сохранении в корзину пользователь не найден")
        );

        Cart cart = new Cart();
        cart.setPerson(person);
        cart.setAmount(1);
        cart.setProduct(productService.findProductById(productId));
        cartRepository.save(cart);
    }

    @Transactional
    public void addAmountCart(int productId, String username){
        Person person = personService.loadUserByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "При увеличении количества товара пользователь не был найден")
        );
        Product product = productService.findProductById(productId);

        Cart cart = cartRepository.findCartByProductAndPerson(product, person).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Корзина не найдена")
        );
        if (product.getAmount() < cart.getAmount() + 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Превышено количество товара на складе");
        }
        else {
            cart.setAmount(cart.getAmount() + 1);

            cartRepository.save(cart);
        }
    }

    @Transactional
    public void formOrder(String username){
        Person person = personService.loadUserByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "При формировании заказа пользователь не был найден")
        );

        List<Cart> carts = cartRepository.findAllByPerson(person);

        if (carts.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Корзина пуста");
        }

        for (Cart cart : carts){
            productService.decreaseProductAmount(cart.getProduct(), cart.getAmount());
            cartRepository.delete(cart);
        }
    }

    @Transactional
    public void decreaseAmountCart(int productId, String username){
        Person person = personService.loadUserByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "При уменьшении количества товара пользователь не был найден")
        );
        Product product = productService.findProductById(productId);

        Cart cart = cartRepository.findCartByProductAndPerson(product, person).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Корзина не найдена")
        );
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

    public CartPriceResponse getPriceCart(String username){

        List<Cart> carts = findByPersonName(username);

        int price = 0;

        if (carts.isEmpty()){
            return formCartPriceResponse(price);
        }

        for (Cart cart : carts){
            price += cart.getProduct().getPrice();
        }

        return formCartPriceResponse(price);
    }

    public void deleteCart(int id){
        cartRepository.deleteById(id);
    }

    private CartPriceResponse formCartPriceResponse(int price){
        CartPriceResponse cartPriceResponse = new CartPriceResponse();
        cartPriceResponse.setPrice(price);
        return cartPriceResponse;
    }

}
