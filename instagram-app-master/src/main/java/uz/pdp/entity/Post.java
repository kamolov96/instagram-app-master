package uz.pdp.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Likes> likes;

    @OneToMany
    private List<Attachment> attachments;

    @CreationTimestamp //avtomatik yozadi
    private Timestamp createdDate;

    private Boolean active=true;

}
