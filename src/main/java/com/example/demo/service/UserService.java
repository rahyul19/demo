package com.example.demo.service;

import com.example.demo.dto.ResetPasswordDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserFetchDTO;
import com.example.demo.dto.UserLoginDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok().body("User Has Been Successfuly Registered");
    }

    public ResponseEntity<String> userLogin(UserLoginDTO userLoginDTO) {
        Optional<User> user = userRepository.findByUserName(userLoginDTO.getUserName());
        if(user.isPresent()) {
            if(user.get().getPassword().equals(userLoginDTO.getPassword())) {
                return ResponseEntity.ok().body("The User Has been successfully Logged in");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Password ");
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No User found for this userName ");
    }

    public ResponseEntity<List<UserFetchDTO>> fetchUsers() {
        List<User> users = userRepository.findAll();
        List<UserFetchDTO> responseDtos = new ArrayList<>();
        users.forEach(user -> {
            UserFetchDTO userFetchDTO = new UserFetchDTO();
            userFetchDTO.setUserName(user.getUserName());
            userFetchDTO.setEmail(user.getEmail());
            userFetchDTO.setName(user.getName());
            responseDtos.add(userFetchDTO);
        });
        return ResponseEntity.ok().body(responseDtos);
    }

    public ResponseEntity<String> resetPassword(ResetPasswordDTO request) {
        Optional<User> user = userRepository.findByUserName(request.getUserName());
        if(user.isPresent()) {
            if(user.get().getPassword().equals(request.getOldPassword())) {
                user.get().setPassword(request.getNewPassword());
                userRepository.save(user.get());
                return ResponseEntity.ok().body("Password Changed Successfully");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password Incorrect");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user Found");
    }
}
