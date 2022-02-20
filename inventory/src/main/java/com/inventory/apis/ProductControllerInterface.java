package com.inventory.apis;

import com.inventory.entities.Product;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product")
public interface ProductControllerInterface {
    @GetMapping()
    public ResponseEntity getListProducts();

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Long id);
  
    @PutMapping()
    public ResponseEntity editProduct(@RequestBody Product product);

    @PostMapping()
    public ResponseEntity addProduct(@RequestBody Product product);

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id);
  
    @GetMapping("/name/{name}")
    public ResponseEntity findByName(@PathVariable String name);
}
