package com.belhard.bookstore.dao.repository;

import com.belhard.bookstore.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User where deleted = false")
    List<User> findAll();

    @Query("select u from User u where u.lastName =?1 and deleted = false")
    User findUserByEmail(String email);

    @Query("select u from User u where u.email =?1 and deleted = false")
    List<User> findUserByLastName(String lastName);

    @Modifying
    @Query(value = "update users u set deleted = true where u.id =:id and deleted = false", nativeQuery = true)
    void delUser(@Param("id") Long id);
}
