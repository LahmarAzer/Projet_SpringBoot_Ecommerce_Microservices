package com.example.cartservice.Services;

import com.example.cartservice.Entities.Discount;

import java.util.List;

public interface DiscountService {
    Discount addDiscount(Discount entity);
    Discount updateDiscount(Discount entity);
    Discount retrieveById(Long id);
    List<Discount> retrieveAll();
    Boolean deleteDiscount(Long id);
    String applyDiscount(String discountCode, Long cartId, String userId);
    void assignDiscountToUser(String idUser, Long idDiscount) ;

}
