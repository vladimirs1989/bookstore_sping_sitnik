package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUser(Pageable pageable);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    List<UserDto> getUserByLastname(String lastName);

    UserDto createUser (UserDto userDto);

    UserDto updateUser (UserDto userDto);

    void deleteUser (Long id);

    int countAllUsers();
}
