package com.inventory.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;

import com.inventory.entities.UserDb;
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
  final private long millis = System.currentTimeMillis();


  @Test
  public void testAddUser() {
    UserDb user = new UserDb(1l, "name", "last", "cc", "102020202", Date.valueOf("2000-03-23"), new Date(millis), "position", 100.0);          
    when(userRepository.save(any())).thenReturn(user);
    UserDb actualUser = userService.addUser(user);
    assertEquals(user.getId(), actualUser.getId());
  }

  @Test
  public void testAddUserNotValid() {
    UserDb user = new UserDb(1l, "name", "last", "cc", "102020202", Date.valueOf("2005-03-23"), new Date(millis), "position", 100.0);                          
    Throwable throwable = catchThrowable(() -> userService.addUser(user));
    assertNotNull(throwable);
    assertThat(throwable).isInstanceOf(UserException.class);
    UserException userException = (UserException)throwable;
    assertEquals("User not valid", userException.getMessage());
  }
}