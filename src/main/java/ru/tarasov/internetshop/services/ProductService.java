package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<Product> getAllProductsByCategory(int id){
        return productRepository.findProductsByCategoryId(id);
    }

}
