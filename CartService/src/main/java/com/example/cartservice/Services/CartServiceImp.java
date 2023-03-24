package com.example.cartservice.Services;

import com.example.cartservice.Entities.*;
import com.example.cartservice.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
public class CartServiceImp implements CartService{

    @Autowired
    CartRepository cartRepository;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    CartLineRepository cartLineRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Cart add(Cart cart) {
        return cartRepository.save(cart);

    }

    public void removeCartLine(CartLine cartLine) {
        cartLineRepository.delete(cartLine);
    }
    @Override
    public Cart update(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart retrieveById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cart> retrieveAll() {
        return cartRepository.findAll();
    }

    @Override
    public Boolean delete(Long id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
            return true;
        }
        else {return false;}
    }


    private static final double taxes = 0.08;
    @Override
    public double calculerMontantTotal(Long idCart) {
        Cart cart = cartRepository.findById(idCart).orElse(null);
        double montantTotalWithoutTaxes = 0.0;
        if (cart != null) {
            for (CartLine cartLine : cart.getCartLines()) {
                Product product = cartLine.getProduct();
                double prixUnitaire = product.getPriceProduct();
                int quantity = cartLine.getQuantity();
                if(quantity > 100) {
                    prixUnitaire -= prixUnitaire * 0.01;
                }
                montantTotalWithoutTaxes += prixUnitaire * quantity;
            }
            double montantTVA = montantTotalWithoutTaxes * taxes;
            double montantTotalWithTaxes = montantTotalWithoutTaxes + montantTVA;
            cart.setTotalCartPrice(montantTotalWithTaxes);
            return montantTotalWithTaxes;
        }
        return 0;
    }


    public List<Product> getRecommendedProducts(String userId) {
        // Get the user object based on the user ID
        User user = userRepository.findById(userId).orElse(null);
        // Get the user's age
        int userAge = 0;
        if (user != null) {
            userAge = user.getAge();
        }
        // Get all the carts that belong to the user except the carts with Pending status
        List<Cart> userCarts = cartRepository.findByUserAndCartStatusIn(user, Arrays.asList(CartStatus.CONFIRMED, CartStatus.WISHLIST));
        // Get all the products that have been ordered or saved in wishlist by the user
        List<Product> userProducts = new ArrayList<>();
        for (Cart cart : userCarts) {
            List<CartLine> cartLines = cart.getCartLines();
            for (CartLine cartLine : cartLines) {
                userProducts.add(cartLine.getProduct());
            }
        }
        // Get all the products that have the same sub-category as the products previously ordered or saved in wishlist by the user
        List<Product> subCategoryProducts = new ArrayList<>();
        List<Sous_CategoryProduct> userSubCategories = userProducts.stream()
                .map(Product::getSousCategorie)
                .collect(Collectors.toList());
        for (Sous_CategoryProduct subCategory : userSubCategories) {
            subCategoryProducts.addAll(subCategory.getProductListSousCategories());
        }
        // Get all the products that have been ordered by all users of the same age
        List<Product> ageOrderedProducts = new ArrayList<>();
        List<User> usersWithSameAge = userRepository.findByAge(userAge);
        List<Cart> carts = cartRepository.findByCartStatusAndUserIn(CartStatus.CONFIRMED,usersWithSameAge);
        for (Cart c: carts) {
            List<CartLine> cartLines = c.getCartLines();
            for (CartLine cartLine : cartLines) {
                ageOrderedProducts.add(cartLine.getProduct());
            }
        }
        // Find the intersection of the sub-category products and the age-ordered products
        List<Product> recommendedProducts = subCategoryProducts.stream()
                .filter(ageOrderedProducts::contains)
                .collect(Collectors.toList());
        return new ArrayList<>(new LinkedHashSet<>(recommendedProducts));
    }




    @Transactional
    public String confirmCartToOrder(Long cartId)  {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
        if (cart.getCartStatus() == CartStatus.CONFIRMED) {
            return(" cart" + cartId + " has already been confirmed ");
        }
        // Decrease stock of products in cart
        for (CartLine cartLine : cart.getCartLines()) {
            Product product = cartLine.getProduct();
            Long stock = product.getQuantityProduct();
            int quantity = cartLine.getQuantity();
            if (quantity > stock ) {
                return ("Insufficient stock for product: " + product.getNameProduct());
            }
            stock -= quantity;
            product.setQuantityProduct(stock) ;
            productRepository.save(product);
        }
        // Update cart status to CONFIRMED and save changes
        cart.setCartStatus(CartStatus.CONFIRMED);
        cart.setTotalCartPrice(calculerMontantTotal(cartId));
        cartRepository.save(cart);
        return ("Cart" + cartId + "is successfully Confirmed");
        }
        return "Cart not found";
    }

    public String confirmCartToWishlist(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            if (cart.getCartStatus() == CartStatus.WISHLIST) {
                return (" cart" + cartId + " has already been added to your WISHLIST ");
            }
            if (cart.getCartStatus() == CartStatus.CONFIRMED) {
                List<CartLine> cartLines = cart.getCartLines();
                for (CartLine cartLine : cartLines) {
                    Product product =cartLine.getProduct();
                    int quantity = cartLine.getQuantity();
                    product.setQuantityProduct(product.getQuantityProduct() + quantity);
                    productRepository.save(product);
                    cart.setCartStatus(CartStatus.WISHLIST);
                    cartRepository.save(cart);
                    return ("Your Confirmed Cart" + cartId + " is successfully switched to your WISHLIST");
                }
            } else {
                // Update cart status to WISHLIST and save changes
                cart.setCartStatus(CartStatus.WISHLIST);
                cartRepository.save(cart);
                return ("Cart" + cartId + " is successfully added to your WISHLIST");
            }
        }
            return "Cart not found";
    }

    public String confirmCartToPending(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            if (cart.getCartStatus() == CartStatus.PENDING) {
                return (" cart" + cartId + " status is already PENDING ");
            }
            if (cart.getCartStatus() == CartStatus.CONFIRMED) {
                List<CartLine> cartLines = cart.getCartLines();
                for (CartLine cartLine : cartLines) {
                    Product product =cartLine.getProduct();
                    int quantity = cartLine.getQuantity();
                    product.setQuantityProduct(product.getQuantityProduct() + quantity);
                    productRepository.save(product);
                    cart.setCartStatus(CartStatus.PENDING);
                    cartRepository.save(cart);
                    return ("Your Confirmed Cart" + cartId + " is successfully switched to PENDING");
                }
            } else {
                // Update cart status to WISHLIST and save changes
                cart.setCartStatus(CartStatus.PENDING);
                cartRepository.save(cart);
                return ("Cart" + cartId + " is successfully switched to PENDING");
            }
        }
        return "Cart not found";
    }

        public String addProductToCart (Long productId, String userId,int quantity){
            // Retrieve the product and user objects
            Product product = productRepository.findById(productId).orElse(null);
            User user = userRepository.findById(userId).orElse(null);
            // Check if the product is available for purchase
            if (product != null && (product.getQuantityProduct() == 0 || quantity > product.getQuantityProduct())) {
                return ("Product is not available for purchase.");
            }
            // Check if the user already has a cart or create a new one
            Cart cart = cartRepository.findFirstByUserAndCartStatus(user, CartStatus.PENDING);
            if (cart == null) {
                cart = new Cart();
                cart.setCartStatus(CartStatus.PENDING);
                cart.setUser(user);
                CartLine cartLine = new CartLine();
                cartLine.setProduct(product);
                cartLine.setCart(cart);
                List<CartLine>cartLines = new ArrayList<>();
                cartLines.add(cartLine);
                cart.setCartLines(cartLines);
                cartRepository.save(cart);
                cartLineRepository.save(cartLine);


            }
            // Check if the product is already in the cart or add a new cart line
            boolean productFound = false;
            for (CartLine cartLine : cart.getCartLines()) {
                if (product != null && cartLine.getProduct().getIdProduct().equals(product.getIdProduct())) {
                    cartLine.setQuantity(cartLine.getQuantity() + quantity);
                    cart.setTotalCartPrice(calculerMontantTotal(cart.getIdCart()));
                    productFound = true;
                    cartRepository.save(cart);
                    cartLineRepository.save(cartLine);

                    break;
                }
            }
            if (!productFound) {
                CartLine cartLine = new CartLine();
                cartLine.setCart(cart);
                cartLine.setProduct(product);
                cartLine.setQuantity(quantity);
                cart.getCartLines().add(cartLine);
                cart.setTotalCartPrice(calculerMontantTotal(cart.getIdCart()));
                cartLineRepository.save(cartLine);
                cartRepository.save(cart);
            }
            // Update the cart and product objects

            return ("product is added successfuly");
        }



    public String deleteProductFromCart(Long cartId, Long productId, String userId) {
        Cart cart = cartRepository.findByIdCartAndCartStatus(cartId,CartStatus.PENDING);
        if (cart != null) {
            User user = cart.getUser();
            if (user.getIdUser().equals(userId)) {
                List<CartLine> cartLines = cart.getCartLines();
                CartLine cartLineToRemove = null;
                for (CartLine cartLine : cartLines) {
                    if (cartLine.getProduct().getIdProduct().equals(productId)) {
                        cartLineToRemove = cartLine;
                        break;
                    }
                }
                if (cartLineToRemove != null) {
                    removeCartLine(cartLineToRemove);
                    cart.setTotalCartPrice(calculerMontantTotal(cart.getIdCart()));
                    cartRepository.save(cart);
                    return "Product deleted from cart successfully";
                } else {
                    return "Product not found in cart";
                }
            } else {
                return "Cart does not belong to user";
            }
        } else {
            return "Cart not found";
        }
    }

    public String decreaseProductQuantity(Long cartId, Long productId) {
        // Retrieve the cart and product objects
        Cart cart = cartRepository.findById(cartId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        // Check if the cart and product exist
        if (cart == null || product == null) {
            return "Cart or product does not exist.";
        }
        // Check if the cart status is PENDING
        if (cart.getCartStatus() != CartStatus.PENDING) {
            return "Cannot update a confirmed or a wishlist cart.";
        }
        // Check if the product is in the cart
        boolean productFound = false;
        for (CartLine cartLine : cart.getCartLines()) {
            if (cartLine.getProduct().equals(product)) {
                // Decrease the quantity
                int newQuantity = cartLine.getQuantity() - 1;
                if (newQuantity <= 0) {
                    // Remove the cart line if the new quantity is zero or negative
                    cart.getCartLines().remove(cartLine);
                    cartLineRepository.delete(cartLine);
                } else {
                    // Update the cart line if the new quantity is positive
                    cartLine.setQuantity(newQuantity);
                    cartLineRepository.save(cartLine);
                }
                // Update the cart total price
                cart.setTotalCartPrice(calculerMontantTotal(cart.getIdCart()));
                cartRepository.save(cart);
                productFound = true;
                break;
            }
        }
        // Return an error message if the product is not in the cart
        if (!productFound) {
            return "Product not found in cart.";
        }
        return "Product quantity updated successfully.";
    }


}




