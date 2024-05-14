package ru.tarasov.internetshop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.tarasov.internetshop.models.Cart;
import ru.tarasov.internetshop.security.JWTAuthentication;
import ru.tarasov.internetshop.services.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Все товары в корзине у конкретного пользователя", tags = {"list"})
    @GetMapping("/list")
    public ResponseEntity<?> cartPage(UsernamePasswordAuthenticationToken auth){
        return new ResponseEntity<>(cartService.findByPersonName(auth.getName()), HttpStatus.OK);
    }

    @Operation(summary = "Добавить товар к корзине", tags = {"add"})
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> saveCart(@RequestBody Cart cart){
        try {
            cartService.saveCart(cart);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Удалить товар из корзины", tags = {"delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCart(@PathVariable int id){
        try {
            cartService.deleteCart(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
