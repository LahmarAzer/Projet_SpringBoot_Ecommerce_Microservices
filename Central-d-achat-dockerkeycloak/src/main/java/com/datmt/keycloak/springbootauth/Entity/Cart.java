package com.datmt.keycloak.springbootauth.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCart;
    private double totalCartPrice;
    private String topic;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus;

    @JsonIgnore
    @OneToOne(mappedBy = "cart")
    private Delivery delivery;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    @Column(name = "cartLine")
    private List<CartLine> cartLines;

    @JsonIgnore
    @OneToOne
    private Discount discount;

}
