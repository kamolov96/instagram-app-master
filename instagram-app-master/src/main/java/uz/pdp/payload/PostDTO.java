package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PostDTO {

    private String description;

    private Integer userId;

    @NotNull(message = "Postda rasm yoki video bo'lishi kerak")
    private List<Integer> attachmentIds;
    private List<Integer> likeIds;
    private List<Integer> commentIds;
}
