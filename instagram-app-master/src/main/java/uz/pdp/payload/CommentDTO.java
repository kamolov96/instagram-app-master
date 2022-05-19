package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {

    @NotBlank(message = "Nimadir yozing")
    private String text;

    private Integer postId;

    private Integer userId;


}
