package uz.pdp.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.component.MailSender;
import uz.pdp.entity.User;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.RegisterDto;
import uz.pdp.repository.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    final
    UserRepository userRepository;

    final
    MailSender mailSender;

    final
    PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, MailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUserName(username).get();
    }

    public ApiResponse register(RegisterDto dto) throws MessagingException {
        boolean byUsername = userRepository.existsByUserName(dto.getUserName());
        if (byUsername) {
            return new ApiResponse("This username is already exist",false);
        }
        boolean byEmail = userRepository.existsByEmail(dto.getEmail());
        if (byEmail) {
            return new ApiResponse("This email is already exist",false);
        }
        User user = new User();
        if (dto.getEmail().contains("@")) {
            user.setEmail(dto.getEmail());
        }
        user.setUserName(dto.getUserName());
        user.setFullName(dto.getFullName());
        user.setPassword(dto.getPassword());
        user.setActive(true);



        //4 xonali
        String code = UUID.randomUUID().toString().substring(0, 5).concat(UUID.randomUUID().toString().substring(0, 5));

        user.setEmailCode(code);

        //mail chaqirib xabar jo'natish kerak
        SimpleMailMessage message = new SimpleMailMessage();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.addHeader("content-type", "html/text");
        message.setFrom("pdp@gmail.com");
        message.setTo(dto.getEmail());
        message.setSubject("Confirmation code");
        message.setText(code);
        message.setSentDate(new Date());
        mailSender.getEmail().send(message);
        userRepository.save(user);
        return new ApiResponse("Code is sent to your email. Please verify!",true);
    }
    public ApiResponse verify(String email, String emailCode) {
//        Optional<User> byUserName = userRepository.findByUserName(email);
//        if (!byUserName.isPresent()) return new ApiResponse("Error",false);
//
//        if (!byUserName.get().getPassword().equals(passwordEncoder.encode(password)))
//            return new ApiResponse("Confirmation code is wrong",false);
//        return new ApiResponse("It's a good",true);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getEmailCode().equals(emailCode)){
                // enabledni true qilamiz
                user.setActive(true);
                userRepository.save(user);
                return new ApiResponse("Successfully verified",true,user);
            }
        }
        return new ApiResponse("Something went wrong",false);
    }

}
