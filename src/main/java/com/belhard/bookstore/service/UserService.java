package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUser(int page, int size);

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    List<UserDto> getUserByLastname(String lastName);

    UserDto createUser (UserDto userDto);

    UserDto updateUser (UserDto userDto);

    void deleteUser (Long id);

    int countAllUsers();
}
