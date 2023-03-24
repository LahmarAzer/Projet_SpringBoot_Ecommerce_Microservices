package com.example.cartservice.Repositories;

import com.example.cartservice.Entities.CartLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartLineRepository extends JpaRepository<CartLine, Long> {
}
