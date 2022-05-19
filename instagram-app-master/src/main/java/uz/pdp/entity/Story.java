package uz.pdp.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    private User user;

    @OneToOne
    private Attachment attachment;

    @CreationTimestamp // avtomatik yozadi
    private Timestamp createdDate;

    private Boolean active=true;


}
