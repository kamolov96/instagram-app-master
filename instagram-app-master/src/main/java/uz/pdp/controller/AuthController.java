package uz.pdp.controller;

//import com.example.soliqjwttask.dto.LoginDTO;
//import com.example.soliqjwttask.security.JwtProvider;
//import com.example.soliqjwttask.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import uz.pdp.entity.User;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.LoginDTO;
import uz.pdp.payload.RegisterDto;
import uz.pdp.security.CurrentUser;
import uz.pdp.security.JwtProvider;
import uz.pdp.service.AuthService;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDTO loginDTO){
        String token=jwtProvider.generateToken(loginDTO.getUserName());
        return ResponseEntity.ok().body(token);
    }
    @GetMapping("/me")
    public HttpEntity<?> getMe(@CurrentUser User user) { //Parametr
        return ResponseEntity.ok().body("Mana" + user);
    }


    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto dto) throws MessagingException {
        ApiResponse response = authService.register(dto);
        return ResponseEntity.status(response.isSuccess()?
                HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(response);
    }
    @GetMapping("/verifyEmail")
    public HttpEntity<?> verify(@RequestParam String email, @RequestParam String code) {

        ApiResponse response = authService.verify(email, code);

        return ResponseEntity.status(response.isSuccess()?
                HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }


}
