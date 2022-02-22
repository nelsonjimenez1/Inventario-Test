package com.inventory.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.inventory.entities.Role;
import com.inventory.entities.UserDB;
import com.inventory.exceptions.UserException;
import com.inventory.repositories.UserRepository;
import com.inventory.services.Impl.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserService.class})
public class UserServiceTest {
  @MockBean
  private UserRepository userRepository;
  @Autowired
  private UserService userService;

  @Test 
  public void testGetListUser() {
    List<UserDB> listUserTest = new ArrayList<>();
    listUserTest.add(new UserDB(1l, "name", Role.ADMINISTRADOR, false));
    when(userRepository.findAll()).thenReturn(listUserTest);
    List<UserDB> listUser = userService.getListUser();
    assertEquals(listUserTest.size(), listUser.size());
  }

  @Test 
  public void testGetListUserEmptyList() {
    List<UserDB> listUserTest = new ArrayList<>();
    when(userRepository.findAll()).thenReturn(listUserTest);
    Throwable throwable = catchThrowable(() -> userService.getListUser());
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(UserException.class);
    UserException userException = (UserException)throwable;
    assertEquals("Empty list user", userException.getMessage());
  }

  @Test
  public void testGetUserById() {
    UserDB user = new UserDB(1l, "name", Role.ADMINISTRADOR, true);            
    Optional<UserDB> userOpt = Optional.of(user);
    when(userRepository.findById(anyLong())).thenReturn(userOpt);
    UserDB actualUser = userService.getUserById(1L);
    assertEquals(userOpt.get().getId(), actualUser.getId());
  }

  @Test
  public void testGetUserByIdUserNotFound() {
    UserDB user = null;
    Optional<UserDB> userOpt = Optional.ofNullable(user);
    when(userRepository.findById(anyLong())).thenReturn(userOpt);
    Throwable throwable = catchThrowable(() -> userService.getUserById(2L));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(UserException.class);
    UserException userException = (UserException)throwable;
    assertEquals("User not found", userException.getMessage());
  }

  @Test
  public void testEditUser() {
    UserDB user = new UserDB(1l, "name", Role.ADMINISTRADOR, true);
    Optional<UserDB> userOpt = Optional.of(user);
    when(userRepository.findById(anyLong())).thenReturn(userOpt);
    when(userRepository.save(any())).thenReturn(user);
    UserDB actualUser = userService.editUser(user);
    assertEquals(user.getId(), actualUser.getId());
  }

  @Test
  public void testEditUserUserNotFound() {
    UserDB userEdit = new UserDB(1l, "name", Role.ADMINISTRADOR, true);
    UserDB user = null;
    Optional<UserDB> userOpt = Optional.ofNullable(user);
    when(userRepository.findById(anyLong())).thenReturn(userOpt);
    Throwable throwable = catchThrowable(() -> userService.editUser(userEdit));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(UserException.class);
    UserException userException = (UserException)throwable;
    assertEquals("User not found", userException.getMessage());
  }

  @Test
  public void testAddUser() {
    UserDB user = new UserDB(1l, "name", Role.ADMINISTRADOR, true);            
    when(userRepository.findByName(anyString())).thenReturn(null);
    when(userRepository.save(any())).thenReturn(user);
    UserDB actualUser = userService.addUser(user);
    assertEquals(user.getId(), actualUser.getId());
  }

  @Test
  public void testAddUserRepeatedUser() {
    UserDB user = new UserDB(1l, "name", Role.ADMINISTRADOR, true);                
    when(userRepository.findByName(anyString())).thenReturn(user);
    Throwable throwable = catchThrowable(() -> userService.addUser(user));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(UserException.class);
    UserException userException = (UserException)throwable;
    assertEquals("User repeated name", userException.getMessage());
  }

  @Test
  public void testDeleteUser() {
    UserDB user = new UserDB(1l, "name", Role.ADMINISTRADOR, true);                                
    Optional<UserDB> userOpt = Optional.of(user);
    when(userRepository.findById(anyLong())).thenReturn(userOpt);
    Long actualId = userService.deleteUser(1l);
    assertEquals(user.getId(), actualId);
  }

  @Test
  public void testDeleteUserUSerNotFound() {
    UserDB userDelete = new UserDB(1l, "name", Role.ADMINISTRADOR, true);                
    UserDB user = null;
    Optional<UserDB> userOpt = Optional.ofNullable(user);
    when(userRepository.findById(anyLong())).thenReturn(userOpt);
    Throwable throwable = catchThrowable(() -> userService.deleteUser(userDelete.getId()));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(UserException.class);
    UserException userException = (UserException)throwable;
    assertEquals("User not found", userException.getMessage());
  }

  @Test
  public void testFindByName() {
    List<UserDB> listUserTest = new ArrayList<>();
    listUserTest.add(new UserDB(1l, "name", Role.ADMINISTRADOR, false));             
    when(userRepository.findAllByName(anyString())).thenReturn(listUserTest);
    List<UserDB> listUser = userService.findByName("name");
    assertEquals(listUserTest.size(), listUser.size());
  }

  @Test
  public void testFindByNameUserNotFound() {
    List<UserDB> listUserTest = new ArrayList<>();
    when(userRepository.findAllByName(anyString())).thenReturn(listUserTest);
    Throwable throwable = catchThrowable(() -> userService.findByName("name"));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(UserException.class);
    UserException userException = (UserException)throwable;
    assertEquals("Empty list user", userException.getMessage());
  }
}
