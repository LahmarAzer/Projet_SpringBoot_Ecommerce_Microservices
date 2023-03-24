package com.example.cartservice.Controller;

import com.example.cartservice.Entities.Discount;
import com.example.cartservice.Entities.Product;
import com.example.cartservice.Services.CartService;
import com.example.cartservice.Services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    public CartService cartService;

    @Autowired
    public DiscountService discountService;

    @GetMapping("/hello")
    public String hello(){
        return ("hello Carts");
    }

    @GetMapping("/calculerMontantTotal/{idcart}")
    public double calculerMontantTotal(@PathVariable Long idcart){
        return cartService.calculerMontantTotal(idcart);
    }
    @GetMapping("/getRecommendedProducts/{userId}")
    public List<Product> getRecommendedProducts(@PathVariable String userId){
        return cartService.getRecommendedProducts(userId);
    }
    @PostMapping("/confirmCartToOrder/{cartId}")
    public String confirmCartToOrder(@PathVariable Long cartId){
        return cartService.confirmCartToOrder(cartId);
    }
    @PostMapping("/confirmCartToWishlist/{cartId}")
    public String confirmCartToWishlist(@PathVariable Long cartId){
        return cartService.confirmCartToWishlist(cartId);
    }
    @PostMapping("/confirmCartToPending/{cartId}")
    public String confirmCartToPending(@PathVariable Long cartId){
        return cartService.confirmCartToPending(cartId);
    }

    @PostMapping("/addProductToCart/{productId}/{userId}/{quantity}")
    public String addProductToCart(@PathVariable Long productId,@PathVariable String userId,@PathVariable int quantity){
        return cartService.addProductToCart(productId,userId,quantity);}

    @PutMapping("/deleteProductFromCart/{cartId}/{productId}/{userId}")
    public String deleteProductFromCart(@PathVariable Long cartId,@PathVariable Long productId,@PathVariable String userId) {
        return cartService.deleteProductFromCart(cartId,productId,userId);
    }
    @PostMapping("/decreaseProductQuantity/{cartId}/{productId}")
    public String decreaseProductQuantity(@PathVariable Long cartId,@PathVariable Long productId){
        return cartService.decreaseProductQuantity(cartId,productId);
    }
    @PostMapping("/applyDiscount/{discountCode}/{cartId}/{userId}")
    public String applyDiscount(@PathVariable String discountCode,@PathVariable Long cartId,@PathVariable String userId){
        return discountService.applyDiscount(discountCode,cartId,userId);
    }

    @PutMapping("/assignDiscountToUser/{idUser}/{idDiscount}")
    public void assignDiscountToUser(@PathVariable String idUser,@PathVariable Long idDiscount) {
        discountService.assignDiscountToUser(idUser,idDiscount);
    }
    @PostMapping("/addDiscount")
    Discount addDiscount(@RequestBody Discount discount){
        return discountService.addDiscount(discount);
    }
    @PostMapping("/updateDiscount")
    Discount updateDiscount(@RequestBody Discount discount){
        return discountService.updateDiscount(discount);
    }
    @DeleteMapping("/deleteDiscount/{idDiscount}")
    Boolean deleteDiscount(@PathVariable Long idDiscount){
        return discountService.deleteDiscount(idDiscount);
    }
}
