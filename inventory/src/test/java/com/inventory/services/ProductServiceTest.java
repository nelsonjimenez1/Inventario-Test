package com.inventory.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.Spring;

import com.inventory.entities.Product;
import com.inventory.entities.Role;
import com.inventory.entities.UserDB;
import com.inventory.exceptions.ProductException;
import com.inventory.repositories.ProductRepository;
import com.inventory.services.Impl.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProductService.class})
public class ProductServiceTest {
  @MockBean
  private ProductRepository productRepository;
  @Autowired
  private ProductService productService;
  final private long millis = System.currentTimeMillis();

  @Test 
  public void testGetListProducts() {
    ArrayList<Product> listProductTest = new ArrayList<>();
    long millis=System.currentTimeMillis();  
    listProductTest.add(new Product(1l,"name", 22, new Date(millis), null));
    when(productRepository.findAll()).thenReturn(listProductTest);
    List<Product> listProducts = productService.getListProducts();
    assertEquals(listProductTest.size(), listProducts.size());
  }

  @Test 
  public void testGetListProductsEmptyList() {
    ArrayList<Product> listProductTest = new ArrayList<>();
    when(productRepository.findAll()).thenReturn(listProductTest);
    Throwable throwable = catchThrowable(() -> productService.getListProducts());
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(ProductException.class);
    ProductException productException = (ProductException)throwable;
    assertEquals(productException.getMessage(), "Empty list product");
  }

  @Test
  public void testGetProductById() {
    Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
    Optional<Product> productOpt = Optional.of(product);
    when(productRepository.findById(anyLong())).thenReturn(productOpt);
    Product expectedProduct = productService.getProductById(1L);
    assertEquals(expectedProduct.getId(), productOpt.get().getId());
  }

  @Test
  public void testGetProductByIdProductNotFound() {
    Product product = null;
    Optional<Product> productOpt = Optional.ofNullable(product);
    when(productRepository.findById(anyLong())).thenReturn(productOpt);
    Throwable throwable = catchThrowable(() -> productService.getProductById(2L));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(ProductException.class);
    ProductException productException = (ProductException)throwable;
    assertEquals(productException.getMessage(), "Product not found");
  }

  @Test
  public void testEditProduct() {
    Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
    Optional<Product> productOpt = Optional.of(product);
    when(productRepository.findById(anyLong())).thenReturn(productOpt);
    when(productRepository.save(any())).thenReturn(product);
    Product expectedProduct = productService.editProduct(product);
    assertEquals(expectedProduct.getId(), product.getId());
  }

  @Test
  public void testEditProductProductNotFound() {
    Product productEdit = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
    Product product = null;
    Optional<Product> productOpt = Optional.ofNullable(product);
    when(productRepository.findById(anyLong())).thenReturn(productOpt);
    Throwable throwable = catchThrowable(() -> productService.editProduct(productEdit));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(ProductException.class);
    ProductException productException = (ProductException)throwable;
    assertEquals(productException.getMessage(), "Product not found");
  }

  @Test
  public void testAddProduct() {
    Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
    when(productRepository.findByName(anyString())).thenReturn(null);
    when(productRepository.save(any())).thenReturn(product);
    Product expectedProduct = productService.addProduct(product);
    assertEquals(expectedProduct.getId(), product.getId());
  }

  @Test
  public void testAddProductRepeatedProduct() {
    Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
    when(productRepository.findByName(anyString())).thenReturn(product);
    Throwable throwable = catchThrowable(() -> productService.addProduct(product));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(ProductException.class);
    ProductException productException = (ProductException)throwable;
    assertEquals(productException.getMessage(), "Repeated product");
  }

  @Test
  public void testDeleteProduct() {
    Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
    Optional<Product> productOpt = Optional.of(product);
    when(productRepository.findById(anyLong())).thenReturn(productOpt);
    Long expectedId = productService.deleteProduct(1l);
    assertEquals(expectedId, product.getId());
  }

  @Test
  public void testDeleteProductProductNotFound() {
    Product productDelete = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
    Product product = null;
    Optional<Product> productOpt = Optional.ofNullable(product);
    when(productRepository.findById(anyLong())).thenReturn(productOpt);
    Throwable throwable = catchThrowable(() -> productService.deleteProduct(productDelete.getId()));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(ProductException.class);
    ProductException productException = (ProductException)throwable;
    assertEquals(productException.getMessage(), "Product not found");
  }

  @Test
  public void testFindByName() {
    Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
    when(productRepository.findByName(anyString())).thenReturn(product);
    Product expectedProduct = productService.findByName(product.getName());
    assertEquals(expectedProduct.getId(), product.getId());
  }

  @Test
  public void testFindByNameProductNotFound() {
    Product product = new Product(1l, "name", 22, new Date(millis),
                new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis)));
    when(productRepository.findByName(anyString())).thenReturn(null);
    Throwable throwable = catchThrowable(() -> productService.findByName(product.getName()));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(ProductException.class);
    ProductException productException = (ProductException)throwable;
    assertEquals(productException.getMessage(), "Product not found");
  }
}
