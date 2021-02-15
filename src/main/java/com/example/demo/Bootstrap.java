package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!userRepository.findByUserName("test").isPresent()) {
            User user = new User();
            user.setUserName("test");
            user.setPassword("test");
            user.setEmail("test@gmail.com");
            user.setName("Default User");
            userRepository.save(user);
        }
    }
}

