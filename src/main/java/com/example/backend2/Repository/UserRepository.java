package com.example.backend2.Repository;

import com.example.backend2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUserName(String username);
    User findByUserEmail(String userEmail);
}