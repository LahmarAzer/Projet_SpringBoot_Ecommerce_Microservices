package com.datmt.keycloak.springbootauth.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeComment implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idLike;

    @JsonIgnore
    @ManyToOne
    private CommentPost commentPost;

    @JsonIgnore
    @ManyToOne
    private User user;

}