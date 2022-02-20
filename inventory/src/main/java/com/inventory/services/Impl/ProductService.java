package com.inventory.services.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.inventory.entities.Product;
import com.inventory.exceptions.ProductException;
import com.inventory.repositories.ProductRepository;
import com.inventory.services.ProductServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements ProductServiceInterface {
  @Autowired
  private ProductRepository productRepository;

  public List<Product> getListProducts() {
    List<Product> findAll = productRepository.findAll();
    if (findAll.size() == 0 || findAll == null) {
      throw new ProductException("Empty list product");
    } else {
      return findAll;
    }
  }

  public Product getProductById(Long id) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isPresent()) {
      return product.get();
    } else {
      throw new ProductException("Product not found");
    }
  }

  public Product editProduct(Product product) {
    Optional<Product> productOpt = productRepository.findById(product.getId());
    if (productOpt.isPresent()) {
      try {
        return productRepository.save(product);
      } catch (Exception e) {
        throw new ProductException("Product repeated name");
      }
    } else {
      throw new ProductException("Product not found");
    }
  }

  public Product addProduct(Product product) {
    Product productFind = productRepository.findByName(product.getName());
    if (productFind != null) {
      throw new ProductException("Repeated product");
    } else {
      return productRepository.save(product);
    }
  }

  public Long deleteProduct(Long id) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isPresent()) {
      productRepository.deleteById(id);
      return id;
    } else {
        throw new ProductException("Product not found");
    }
  }

  public Product findByName(String name) {
    Product find = productRepository.findByName(name);
    if (find == null) {
      throw new ProductException("Product not found");
    } else {
      return find;
    }
  }
}
