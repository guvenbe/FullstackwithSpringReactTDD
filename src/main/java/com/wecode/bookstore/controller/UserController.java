package com.wecode.bookstore.controller;

import com.wecode.bookstore.config.JwtUtil;
import com.wecode.bookstore.dto.AuthenticationReponse;
import com.wecode.bookstore.dto.AuthenticationRequest;
import com.wecode.bookstore.dto.UserDto;
import com.wecode.bookstore.service.UserDetailService;
import com.wecode.bookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserDetailService userDetailService;
    private final JwtUtil jwtUtil;

    public UserController(AuthenticationManager authenticationManager, UserDetailService userDetailService, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    ResponseEntity<AuthenticationReponse> authenticate(@RequestBody AuthenticationRequest request){
        try{
            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }catch (BadCredentialsException ex){
            throw new RuntimeException("username and/or password is incorrect");
        }
        //from our database
        UserDetails userDetails = userDetailService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationReponse("Bearer " +token));
    }

    @PostMapping("/register")
    public ResponseEntity<UUID> addUser(@Valid @RequestBody UserDto userDto ){
        UUID uuid = userService.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
    }
}
