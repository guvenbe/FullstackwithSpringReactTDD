package com.wecode.bookstore.service;

import com.wecode.bookstore.dto.UserDto;
import com.wecode.bookstore.model.User;
import com.wecode.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldReturnUserIdWhenCalledWithUserData(){
        UUID id = UUID.randomUUID();
        User user = getUser(id);
        UserDto userDto = getUserDto(id);

        when(userRepository.saveAndFlush(any())).thenReturn(getUser(id));
        when(modelMapper.map(userDto, User.class)).thenReturn(user);

        UUID uuid = userService.addUser(getUserDto(id));

        verify(userRepository, times(1)).saveAndFlush(user);
        verify(modelMapper, times(1)).map(userDto, User.class);
        assertThat(uuid).isNotNull();
        assertThat(uuid).isEqualTo(id);
    }

    @Test
    public void ShouldReturnUsersWhenEmailExists(){
        UUID id = UUID.randomUUID();
        User user = getUser(id);
        UserDto userDto = getUserDto(id);
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);

        UserDto userByEmailDto = userService.getUserByEmail(getUser(id).getEmail());

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(modelMapper, times(1)).map(user,UserDto.class);
        assertThat(userByEmailDto).isNotNull();
        assertThat(userByEmailDto.getId()).isEqualTo(userDto.getId());
        assertThat(userByEmailDto.getName()).isEqualTo(userDto.getName());
        assertThat(userByEmailDto.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(userByEmailDto.getPassword()).isEqualTo(userDto.getPassword());
    }

    @Test
    public void ShouldThrowErrorWhenEmailIsNotExists(){
        UUID id = UUID.randomUUID();
        User user = getUser(id);
        UserDto userDto = getUserDto(id);
        when(userRepository.findByEmail(anyString())).thenThrow(new RuntimeException("error"));
        assertThatThrownBy(() ->
                userService.getUserByEmail(user.getEmail())).isInstanceOf(RuntimeException.class);
    }

    private UserDto getUserDto(UUID uuid) {
        return UserDto.builder()
                .id(uuid)
                .name("username")
                .email("email")
                .password("password")
                .build();
    }

    private User getUser(UUID uuid) {
        return User.builder()
                .id(uuid)
                .name("username")
                .email("email")
                .password("password")
                .build();
    }


}