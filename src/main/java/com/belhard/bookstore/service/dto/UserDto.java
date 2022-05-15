package com.belhard.bookstore.service.dto;

import java.util.Objects;

public class UserDto {
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String login;
    private String password;
    private Integer age;
    private RoleDto role;

    public enum RoleDto{
        ADMIN,
        MANAGER,
        CUSTOMER
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(lastName, userDto.lastName) && Objects.equals(firstName, userDto.firstName) && Objects.equals(email, userDto.email) && Objects.equals(login, userDto.login) && Objects.equals(password, userDto.password) && Objects.equals(age, userDto.age) && role == userDto.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, email, login, password, age, role);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", role=" + role +
                '}';
    }
}
