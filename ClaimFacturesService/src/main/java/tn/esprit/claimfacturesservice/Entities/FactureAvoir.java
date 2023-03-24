package tn.esprit.claimfacturesservice.Entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Builder

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FactureAvoir implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idFactureAvoir;
    private String description;

    @OneToOne(mappedBy = "factureAvoir")
    private Facture facture;

}