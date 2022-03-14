package com.inventory.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.entities.UserDb;
import com.inventory.exceptions.GlobalExceptionHandler;
import com.inventory.exceptions.UserException;
import com.inventory.services.UserServiceInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
        .setControllerAdvice(globalExceptionHandler)
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
  public void testAddUser() throws Exception {
    UserDb user = new UserDb(1l, "name", "last", "cc", "102020202", new Date(millis), new Date(millis), "position", 100.0);
    when(userService.addUser(any())).thenReturn(user);
    this.mockMvc.perform(post("/user")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.age").exists());
  }

  @Test
  public void testAddUserNotValid() throws Exception {
    UserDb user = new UserDb(1l, "name", "last", "cc", "102020202", new Date(millis), new Date(millis), "position", 100.0);
    when(userService.addUser(any())).thenThrow(new UserException("User not valid"));
    this.mockMvc.perform(post("/user")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", is("User not valid")));
  }
}