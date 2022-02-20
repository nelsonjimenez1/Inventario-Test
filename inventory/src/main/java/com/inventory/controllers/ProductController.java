package com.inventory.controllers;

import com.inventory.apis.ProductControllerInterface;
import com.inventory.entities.Product;
import com.inventory.services.ProductServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController implements ProductControllerInterface {

    @Autowired
    private ProductServiceInterface productService;

    public ResponseEntity getListProducts() {
        return new ResponseEntity(productService.getListProducts(), HttpStatus.OK); 
    }

    public ResponseEntity getProductById(@PathVariable Long id) {
        return new ResponseEntity(productService.getProductById(id), HttpStatus.OK);
    }
  
    public ResponseEntity editProduct(@RequestBody Product product) {
        return new ResponseEntity(productService.editProduct(product), HttpStatus.OK);
    }

    public ResponseEntity addProduct(@RequestBody Product product) {
        return new ResponseEntity<Product>(productService.addProduct(product), HttpStatus.CREATED);
    }

    public ResponseEntity deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<String>("El producto con id: " + id.toString() + " fue eliminado", HttpStatus.OK);
    }
    
    public ResponseEntity findByName(@PathVariable String name) {
        return new ResponseEntity(productService.findByName(name), HttpStatus.OK); 
    }
}
