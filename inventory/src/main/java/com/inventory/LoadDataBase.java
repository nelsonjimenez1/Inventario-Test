package com.inventory;

import java.sql.Date;

import com.inventory.entities.Product;
import com.inventory.entities.Role;
import com.inventory.entities.UserDB;
import com.inventory.services.ProductServiceInterface;
import com.inventory.services.UserServiceInterface;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDatabase(UserServiceInterface userService, ProductServiceInterface productService) {
        return args -> {
            UserDB user = userService.getUserByUserWithouthException("admin");
            if (user == null) {
                UserDB admin = new UserDB();
                admin.setUser("admin");
                admin.setPassword("admin");
                admin.setName("admin");
                admin.setAge(22);
                admin.setRole(Role.ADMINISTRADOR_SOPORTE);
                long millis=System.currentTimeMillis();  
                admin.setAdmissionDate(new Date(millis));
                admin = userService.signUp(admin);
            }
        };
    }
}