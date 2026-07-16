package com.example.taskapi.service;

import com.example.taskapi.Entity.User;
import com.example.taskapi.dto.UserCreateRequest;
import com.example.taskapi.dto.UserResponse;
import com.example.taskapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional  // ← 途中でエラーになったらDB変更を全部なかったことにする
    public UserResponse createUser(UserCreateRequest request){

        if (userRepository.existsByUsername(request.username())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getUsername());
    }

    public UserResponse toResponse(User currentUser){
        return new UserResponse(currentUser.getId(), currentUser.getUsername());
    }
}
