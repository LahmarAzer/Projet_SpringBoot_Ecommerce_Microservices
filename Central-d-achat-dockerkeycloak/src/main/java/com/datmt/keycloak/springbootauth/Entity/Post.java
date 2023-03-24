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
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idPost;
    private String topicPost;
    private String descriptionPost;
    @Lob
    private byte[] imagePost;
    @Temporal(TemporalType.DATE)
    private Date dateCreationPost;

    @JsonIgnore
    @ManyToOne
    private CategoryProduct categoryPost;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<CommentPost> commentList;

    @JsonIgnore
    @ManyToOne
    private User userPost;

}