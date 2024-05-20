package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.tarasov.internetshop.models.Product;
import ru.tarasov.internetshop.repositories.ProductRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){

        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findProductById(int id){
        return productRepository.findProductById(id);
    }

    @Transactional
    public void decreaseProductAmount(Product product, int amount){

        if (product.getAmount() - amount < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Товара на складе меньше");
        }

        if (product.getAmount() - amount == 0){
            productRepository.delete(product);
        }
        else {
            product.setAmount(product.getAmount() - amount);
        }

    }

    public List<Product> getAllProductsByCategory(int id){
        return productRepository.findProductsByCategoryId(id);
    }

}
