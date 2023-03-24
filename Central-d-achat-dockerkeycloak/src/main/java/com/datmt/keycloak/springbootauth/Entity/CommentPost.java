package com.datmt.keycloak.springbootauth.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentPost implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idComment;
    @Temporal(TemporalType.DATE)
    private Date dateCreationComment;
    private String descriptionComment;
    private Long nbLiked;
    private Long nbDisliked;

    @JsonIgnore
    @ManyToOne
    private Post post;

    @JsonIgnore
    @ManyToOne
    private User userComment;

    @JsonIgnore
    @OneToOne(mappedBy = "comment",cascade = CascadeType.ALL)
    private Product productForum;


    @JsonIgnore
    @OneToMany(mappedBy = "commentPost",cascade = CascadeType.ALL)
    private List<LikeComment> likeComments ;

    @JsonIgnore
    @OneToMany(mappedBy = "commentPost",cascade = CascadeType.ALL)
    private List<DislikeComment> dislikeComments ;


}