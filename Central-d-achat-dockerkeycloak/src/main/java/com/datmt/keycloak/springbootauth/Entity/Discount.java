package com.datmt.keycloak.springbootauth.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Discount implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long IdDiscount;

    private String discountCode;

    private Date expiryDate;

    @Column(nullable = false)
    private double amount;

    private boolean percentage;

    @Enumerated(EnumType.STRING)
    private DiscountStatus discountStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User usedBy;

    @JsonIgnore
    @OneToOne
    private Cart cart;

}
