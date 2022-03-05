package com.wecode.bookstore.service;

import com.wecode.bookstore.dto.UserDto;
import com.wecode.bookstore.model.User;
import com.wecode.bookstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UUID addUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setId(null);
        User createdUser = userRepository.saveAndFlush(user);
        return createdUser.getId();
    }

    public UserDto getUserByEmail(String email){

        User userByEmail = userRepository.findByEmail(email);
        if(Objects.isNull(userByEmail)){
            throw new RuntimeException("user does not exits");
        }
        return modelMapper.map(userByEmail, UserDto.class);
    }
}
