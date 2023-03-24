package com.example.cartservice.Services;

import com.example.cartservice.Entities.Cart;
import com.example.cartservice.Entities.Product;

import java.util.List;

public interface CartService {
    Cart add(Cart entity);
    Cart update(Cart entity);
    Cart retrieveById(Long id);
    List<Cart> retrieveAll();
    Boolean delete(Long id);
    double calculerMontantTotal(Long idcart);
    List<Product> getRecommendedProducts(String userId);
    String confirmCartToOrder(Long cartId);
     String addProductToCart(Long productId, String userId, int quantity);
    String confirmCartToWishlist(Long cartId);
    String confirmCartToPending(Long cartId);

    String deleteProductFromCart(Long cartId, Long productId, String userId);

    String decreaseProductQuantity(Long cartId, Long productId);
    }
