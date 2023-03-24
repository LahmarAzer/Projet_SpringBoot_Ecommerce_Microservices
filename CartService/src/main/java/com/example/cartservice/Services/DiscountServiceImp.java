package com.example.cartservice.Services;

import com.example.cartservice.Entities.*;
import com.example.cartservice.Repositories.CartRepository;
import com.example.cartservice.Repositories.DiscountRepository;
import com.example.cartservice.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DiscountServiceImp implements DiscountService{
    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;
    private final SmsService smsService;

    public DiscountServiceImp(SmsService smsService) {
        this.smsService = smsService;
    }

    @Override
    public Discount addDiscount(Discount discount) {
        return discountRepository.save(discount);

    }

    @Override
    public Discount updateDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    @Override
    public Discount retrieveById(Long id) {
        return discountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Discount> retrieveAll() {
        return discountRepository.findAll();
    }

    @Override
    public Boolean deleteDiscount(Long id) {
        if (discountRepository.existsById(id)) {
            discountRepository.deleteById(id);
            return true;
        }
        else {return false;}
    }

    @Scheduled(fixedDelay = 60000)
    public void expireDiscounts() {
        Date now = new Date();
        List<Discount> expiredDiscounts = discountRepository.findByExpiryDateBeforeAndDiscountStatus(now, DiscountStatus.VALID);
        if (expiredDiscounts == null){
            log.info("no discount expired");
        }
        for (Discount discount:expiredDiscounts) {
            discount.setDiscountStatus(DiscountStatus.EXPIRED);
            discountRepository.save(discount);
            log.info("Discount: " + discount.getIdDiscount() + "  Expired");
        }
    }
    public String applyDiscount(String discountCode, Long cartId, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "User not found";
        }
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return "Cart not found";
        }
        if (!cart.getUser().equals(user)) {
            return "Cart does not belong to user";
        }
        Discount discount = discountRepository.findByDiscountCode(discountCode);
        if (discount == null) {
            return "Discount code is invalid";
        }
        if (discount.getUsedBy() != null && !discount.getUsedBy().equals(user)) {
            return "Discount code is not valid for this user";
        }
        if (discount.getDiscountStatus() == DiscountStatus.EXPIRED ) {
            return "Discount code has expired";
        }
        if (cart.getDiscount() != null) {
            return "Discount code has already been applied to cart";
        }
        double total = cart.getTotalCartPrice();
        double discountAmount;
        if (discount.isPercentage() ) {
            discountAmount = total * (discount.getAmount()/100);
        } else  {
            discountAmount = discount.getAmount();
        }
        double finalAmount = total-discountAmount;
        if (finalAmount < 0) {
            return "Discount amount exceeds cart total amount";
        }
        cart.setTotalCartPrice(finalAmount);
        cart.setDiscount(discount);
        discount.setUsedBy(user);
        discount.setDiscountStatus(DiscountStatus.EXPIRED);
        discount.setCart(cart);
        discountRepository.save(discount);
        cartRepository.save(cart);
        return "Discount code has been applied successfully ! Your Final Amount will be "+ finalAmount;

    }
    public void assignDiscountToUser(String idUser, Long idDiscount) {
        User user = userRepository.findById(idUser).orElse(null);
        Discount discount = discountRepository.findById(idDiscount).orElse(null);

        if (user != null && discount != null) {
            user.getDiscounts().add(discount);

            userRepository.save(user);

            String message = ("Congratulations! You have been awarded a" + discount.getAmount() + " discount. Use code " + discount.getDiscountCode() + " at checkout.");

            smsService.sendSms(user.getPhone(), message);
        }
    }
}
