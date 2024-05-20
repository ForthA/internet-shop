package ru.tarasov.internetshop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.tarasov.internetshop.requests.CartAddRequest;
import ru.tarasov.internetshop.requests.CartAmountRequest;
import ru.tarasov.internetshop.requests.CartDeleteRequest;
import ru.tarasov.internetshop.services.CartService;

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
    public ResponseEntity<HttpStatus> saveCart(@RequestBody CartAddRequest cartAddRequest,
                                               UsernamePasswordAuthenticationToken auth){
        try {
            cartService.saveCart(cartAddRequest.getId(), auth.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Увеличить количество товара в корзине", tags = {"add"})
    @PatchMapping("/addAmount")
    public ResponseEntity<HttpStatus> addAmount(@RequestBody CartAmountRequest cartAmountRequest,
                                                UsernamePasswordAuthenticationToken auth) {
        try {
            cartService.addAmountCart(cartAmountRequest.getId(), auth.getName());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Уменьшите количество товара в корзине", tags = {"delete"})
    @PatchMapping("/decreaseAmount")
    public ResponseEntity<HttpStatus> decreaseAmount(@RequestBody CartAmountRequest cartAmountRequest,
                                                     UsernamePasswordAuthenticationToken auth) {
        try{
            cartService.decreaseAmountCart(cartAmountRequest.getId(), auth.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Удалить товар из корзины", tags = {"delete"})
    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteCart(@RequestBody CartDeleteRequest cartDeleteRequest,
                                                 UsernamePasswordAuthenticationToken auth){
        try {
            cartService.deleteCart(cartDeleteRequest.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Получить стоимость заказа", tags = {"get"})
    @GetMapping("/cart_price")
    public ResponseEntity<?> cartPrice(UsernamePasswordAuthenticationToken auth){

        try {
            return ResponseEntity.ok(cartService.getPriceCart(auth.getName()));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Оформить заказ", tags = "form")
    @PatchMapping("/form_order")
    public ResponseEntity<HttpStatus> formOrder(UsernamePasswordAuthenticationToken auth){
        try {
            cartService.formOrder(auth.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
