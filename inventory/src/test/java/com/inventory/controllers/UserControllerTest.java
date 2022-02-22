package com.inventory.controllers;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.entities.Role;
import com.inventory.entities.UserDB;
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
  public void testGetListUser() throws Exception {
    List<UserDB> listUserTest = new ArrayList<>();
    listUserTest.add(new UserDB(1l, "name", Role.ADMINISTRADOR, false));
    when(userService.getListUser()).thenReturn(listUserTest);
    this.mockMvc.perform(get("/user")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[*].name", hasItem("name")));
  }

  @Test
  public void testGetListUserEmptyList() throws Exception {
    when(userService.getListUser()).thenThrow(new UserException("Empty list user"));
    this.mockMvc.perform(get("/user")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", is("Empty list user")));
  }

  @Test
  public void testGetUserById() throws Exception {
    Long id = 1l;
    UserDB user = new UserDB(1l, "name", Role.ADMINISTRADOR, true);
    when(userService.getUserById(anyLong())).thenReturn(user);
    this.mockMvc.perform(get("/user/{id}", id)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("name")));
  }

  @Test
  public void testGetUserByIdUserNotFound() throws Exception {
    Long id = 2l;
    when(userService.getUserById(anyLong())).thenThrow(new UserException("User not found"));
    this.mockMvc.perform(get("/user/{id}", id)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", is("User not found")));
  }

  @Test
  public void testEditUser() throws Exception {
    UserDB user = new UserDB(1l, "name", Role.ADMINISTRADOR, true);
    when(userService.editUser(any())).thenReturn(user);
    this.mockMvc.perform(put("/user")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void testEditUserRepeatedName() throws Exception {
    UserDB user = new UserDB(1l, "name", Role.ADMINISTRADOR, true);
    when(userService.editUser(any())).thenThrow(new UserException("User repeated name"));
    this.mockMvc.perform(put("/user")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", is("User repeated name")));
  }

  @Test
  public void testAddUser() throws Exception {
    UserDB user = new UserDB(1l, "name", Role.ADMINISTRADOR, true);
    when(userService.addUser(any())).thenReturn(user);
    this.mockMvc.perform(post("/user")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void testAddUserRepeatedName() throws Exception {
    UserDB user = new UserDB(1l, "name", Role.ADMINISTRADOR, true);
    when(userService.addUser(any())).thenThrow(new UserException("User repeated name"));
    this.mockMvc.perform(post("/user")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", is("User repeated name")));
  }

  @Test
  public void testDeleteUser() throws Exception {
    Long id = 1l;
    when(userService.deleteUser(anyLong())).thenReturn(id);
    this.mockMvc.perform(delete("/user/{id}", id)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$",
            is("The user with id: " + id.toString() + " was removed")));
  }

  @Test
  public void testDeleteUserUserNotFound() throws Exception {
    Long id = 2l;
    when(userService.deleteUser(anyLong())).thenThrow(new UserException("User not found"));
    this.mockMvc.perform(delete("/user/{id}", id)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", is("User not found")));
  }

  @Test
  public void tesFindByName() throws Exception {
    List<UserDB> listUserTest = new ArrayList<>();
    listUserTest.add(new UserDB(1l, "name", Role.ADMINISTRADOR, false));
    when(userService.findByName(anyString())).thenReturn(listUserTest);
    this.mockMvc.perform(get("/user/name/{name}", "name")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[*].name", hasItem("name")));
  }

  @Test
  public void testFindByNameEmptyList() throws Exception {
    when(userService.findByName(anyString())).thenThrow(new UserException("Empty list user"));
    this.mockMvc.perform(get("/user/name/{name}", "name")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", is("Empty list user")));
  }
}
