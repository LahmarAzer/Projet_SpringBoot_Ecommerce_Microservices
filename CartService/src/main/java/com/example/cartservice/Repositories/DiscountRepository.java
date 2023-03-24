package com.example.cartservice.Repositories;

import com.example.cartservice.Entities.Discount;
import com.example.cartservice.Entities.DiscountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findByDiscountCode(String discountCode);
    List<Discount> findByExpiryDateBeforeAndDiscountStatus(Date now, DiscountStatus status);}
