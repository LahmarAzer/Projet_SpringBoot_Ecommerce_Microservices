package tn.esprit.msstore.Entity;

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
public class DislikeComment implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idDislike;

    @JsonIgnore
    @ManyToOne
    private CommentPost commentPost;

    @JsonIgnore
    @ManyToOne
    private User user;
}
