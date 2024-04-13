package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tarasov.internetshop.models.Cart;
import ru.tarasov.internetshop.repositories.CartRepository;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
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
