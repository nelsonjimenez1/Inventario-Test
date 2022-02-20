package com.inventory.services;

import java.util.List;

import com.inventory.entities.Product;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public interface ProductServiceInterface {
    
    public List<Product> getListProducts();

    public Product getProductById(Long id);
    
    public Product editProduct(Product product);
    
    public Product addProduct(Product product);

    public Long deleteProduct(Long id);

    public Product findByName(String name);
}
