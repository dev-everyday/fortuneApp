package com.fortune.app.user.repository;

import com.fortune.app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.name = :name")
    Optional<User> findByName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.nickname = :nickname")
    Optional<User> findUserByNickname(@Param("nickname") String nickname);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.nickname = :nickname")
    boolean existsByNickname(@Param("nickname") String nickname);
}