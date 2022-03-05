package com.wecode.bookstore.service;

import com.wecode.bookstore.dto.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserService userService;
    //private final PasswordEncoder passwordEncoder;


    public UserDetailService(UserService userService) {//PasswordEncoder passwordEncoder) {
        this.userService = userService;
        //this.passwordEncoder = passwordEncoder;
    }

    //this is the function which is used by spring security for loading user from db
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userByEmail = userService.getUserByEmail(username);
        return new User(userByEmail.getEmail(), userByEmail.getPassword(), new ArrayList<>());
//        return new User("peter@gmail.com", passwordEncoder.encode("password"), new ArrayList<>());
    }
}
