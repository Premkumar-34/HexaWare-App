package com.example.recruiting_application.repository;

import com.example.recruiting_application.model.ForgetPassword;
import com.example.recruiting_application.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgetPassRepo extends JpaRepository<ForgetPassword, Long> {
    Optional<ForgetPassword> findByUserAndOtp(User user, Integer otp);
    Optional<ForgetPassword> findByOtp(Integer otp);

    Optional<ForgetPassword> findByUserEmailAndOtp(String email, Integer otp);

}
