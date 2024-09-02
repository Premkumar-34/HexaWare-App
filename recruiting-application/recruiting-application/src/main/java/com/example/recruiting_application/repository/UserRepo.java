package com.example.recruiting_application.repository;

import com.example.recruiting_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.verificationToken = :token")
    boolean isTokenValid(@Param("token") String token);

    @Query("UPDATE User u SET u.isVerified = true WHERE u.verificationToken = :token")
    void verifyUserEmail(@Param("token") String token);
}
