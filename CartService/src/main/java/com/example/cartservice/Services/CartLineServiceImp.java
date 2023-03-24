package com.example.cartservice.Services;

import com.example.cartservice.Entities.Cart;
import com.example.cartservice.Entities.CartLine;
import com.example.cartservice.Repositories.CartLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartLineServiceImp implements CartLineService{
    @Autowired
    CartLineRepository cartLineRepository;

    @Override
    public CartLine add(CartLine cartLine) {
        return cartLineRepository.save(cartLine);

    }

    public void removeCartLine(CartLine cartLine) {
        cartLineRepository.delete(cartLine);
    }
    @Override
    public CartLine update(CartLine cartLine) {
        return cartLineRepository.save(cartLine);
    }

    @Override
    public CartLine retrieveById(Long id) {
        return cartLineRepository.findById(id).orElse(null);
    }

    @Override
    public List<CartLine> retrieveAll() {
        return cartLineRepository.findAll();
    }

    @Override
    public Boolean delete(Long id) {
        if (cartLineRepository.existsById(id)) {
            cartLineRepository.deleteById(id);
            return true;
        }
        else {return false;}
    }
}
