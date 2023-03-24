package com.example.cartservice.Repositories;

import com.example.cartservice.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByAge(int ages);
}
