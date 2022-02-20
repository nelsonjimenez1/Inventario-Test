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
import com.inventory.exceptions.UserException;
import com.inventory.services.ProductServiceInterface;
import com.inventory.services.UserServiceInterface;

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

@WebMvcTest({ UserController.class, GlobalExceptionHandler.class })
public class UserControllerTest {

  @MockBean
  private UserServiceInterface userService;
  @Autowired
  private UserController userController;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private GlobalExceptionHandler globalExceptionHandler;
  final private long millis = System.currentTimeMillis();

  @BeforeEach
  public void init() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(globalExceptionHandler).build();
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
  public void testLogin() throws Exception {
    UserDB user = new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis));
    when(userService.login(any())).thenReturn(user);
    this.mockMvc.perform(post("/user/login")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void testSignUp() throws Exception {
    UserDB user = new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis));
    when(userService.signUp(any())).thenReturn(user);
    this.mockMvc.perform(post("/user")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void testSignUpRepeatedUser() throws Exception {
    UserDB user = new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis));
    when(userService.signUp(any())).thenThrow(new UserException("Repeated user"));
    this.mockMvc.perform(post("/user")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", is("Repeated user")));
  }

  @Test
  public void testGetUserByUser() throws Exception {
    String user = "user";
    UserDB userDB = new UserDB(1l, "user", "pass", "name", 12, Role.ADMINISTRADOR_SOPORTE, new Date(millis));  
    when(userService.getUserByUser(anyString())).thenReturn(userDB);
    this.mockMvc.perform(get("/user/{user}", user)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user", is("user")));
  }

  @Test
  public void testGetUserByUserProductNotFound() throws Exception {
    String user = "user";
    when(userService.getUserByUser(anyString())).thenThrow(new UserException("Product not found"));
    this.mockMvc.perform(get("/user/{user}", user)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
