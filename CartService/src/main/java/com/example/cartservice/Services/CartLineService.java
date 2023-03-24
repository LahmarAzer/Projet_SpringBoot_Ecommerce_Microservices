package com.example.cartservice.Services;

import com.example.cartservice.Entities.Cart;
import com.example.cartservice.Entities.CartLine;

import java.util.List;

public interface CartLineService {
    void removeCartLine(CartLine cartLine);
    CartLine add(CartLine entity);
    CartLine update(CartLine entity);
    CartLine retrieveById(Long id);
    List<CartLine> retrieveAll();
    Boolean delete(Long id);
}
