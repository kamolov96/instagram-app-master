package uz.pdp.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {

    private String email;
    private String fullName;
    private String userName;
    private String password;
}
