package com.inventory.controllers;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.apis.ProductControllerInterface;
import com.inventory.entities.Product;
import com.inventory.entities.Role;
import com.inventory.entities.UserDB;
import com.inventory.exceptions.GlobalExceptionHandler;
import com.inventory.exceptions.ProductException;
import com.inventory.services.ProductServiceInterface;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import org.junit.jupiter.api.BeforeEach;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest({ ProductController.class, GlobalExceptionHandler.class })
public class ProductControllerTest {

    @MockBean
    private ProductServiceInterface productService;
    @Autowired
    private ProductController productController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;
    final private long millis = System.currentTimeMillis();

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).setControllerAdvice(globalExceptionHandler)
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetListProducts() throws Exception {
        ArrayList<Product> listProductTest = new ArrayList<>();
        listProductTest.add(new Product(1l, "name", 22, new Date(millis), null));
        when(productService.getListProducts()).thenReturn(listProductTest);
        this.mockMvc.perform(get("/product")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].name", hasItem("name")));
    }

    @Test
    public void testGetListProductsEmptyList() throws Exception {
        when(productService.getListProducts()).thenThrow(new ProductException("Empty list product"));
        this.mockMvc.perform(get("/product")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Empty list product")));
    }

    @Test
    public void testGetProductById() throws Exception {
        Long id = 1l;
        Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
        when(productService.getProductById(anyLong())).thenReturn(product);
        this.mockMvc.perform(get("/product/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name")));
    }

    @Test
    public void testGetProductByIdProductNotFound() throws Exception {
        Long id = 2l;
        when(productService.getProductById(anyLong())).thenThrow(new ProductException("Product not found"));
        this.mockMvc.perform(get("/product/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Product not found")));
    }

    @Test
    public void testEditProduct() throws Exception {
        Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
        when(productService.editProduct(any())).thenReturn(product);
        this.mockMvc.perform(put("/product")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testEditProductRepeatedName() throws Exception {
        Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
        when(productService.editProduct(any())).thenThrow(new ProductException("Product repeated name"));
        this.mockMvc.perform(put("/product")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(product)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Product repeated name")));
    }

    @Test
    public void testAddProduct() throws Exception {
        Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
        when(productService.addProduct(any())).thenReturn(product);
        this.mockMvc.perform(post("/product")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testAddProductRepeatedProduct() throws Exception {
        Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
        when(productService.addProduct(any())).thenThrow(new ProductException("Repeated product"));
        this.mockMvc.perform(post("/product")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(product)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Repeated product")));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long id = 1l;
        when(productService.deleteProduct(anyLong())).thenReturn(id);
        this.mockMvc.perform(delete("/product/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("El producto con id: " + id.toString() + " fue eliminado")));
    }

    @Test
    public void testDdleteProductProductNotFound() throws Exception {
        Long id = 2l;
        when(productService.deleteProduct(anyLong())).thenThrow(new ProductException("Product not found"));
        this.mockMvc.perform(delete("/product/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Product not found")));
    }

    @Test
    public void testFindByName() throws Exception {
        String name = "name";
        Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
        when(productService.findByName(anyString())).thenReturn(product);
        this.mockMvc.perform(get("/product/name/{name}", name)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name")));
    }

    @Test
    public void testFindByNameProductNotFound() throws Exception {
        String name = "name";
        when(productService.findByName(anyString())).thenThrow(new ProductException("Product not found"));
        this.mockMvc.perform(get("/product/name{name}", name)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
