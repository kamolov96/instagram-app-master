package uz.pdp.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String text;



    @ManyToOne
    private User kimdan;

    @ManyToOne
    private User kimga;


    @ManyToOne
    @JoinColumn(name = "user_messenger_id")
    private UserMessenger userMessenger;

}
