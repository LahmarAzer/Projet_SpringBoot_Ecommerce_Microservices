package com.example.cartservice.Repositories;

import com.example.cartservice.Entities.Cart;
import com.example.cartservice.Entities.CartStatus;
import com.example.cartservice.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserAndCartStatusIn(User user,List<CartStatus>cartStatuses);
    List<Cart> findByCartStatusAndUserIn(CartStatus cartStatus,List<User> users);
    Cart findFirstByUserAndCartStatus(User user,CartStatus cartStatus);
    Cart findByIdCartAndCartStatus(Long idCart,CartStatus cartStatus);

}
