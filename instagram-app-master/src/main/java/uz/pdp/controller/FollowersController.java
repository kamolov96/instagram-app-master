package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.User;
import uz.pdp.payload.FollowersDTO;
import uz.pdp.repository.FollowersRepository;
import uz.pdp.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/followers")
@RequiredArgsConstructor
public class FollowersController {

    final FollowersRepository followersRepository;
    final UserRepository userRepository;

    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok().body(followersRepository.findAll());
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        return ResponseEntity.ok().body(followersRepository.findById(id).get());
    }
    @GetMapping("/search")
    public HttpEntity<?> search(@RequestBody FollowersDTO dto){
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok().body(followersRepository.findAllByUser_FullNameContainingIgnoreCase(user.getFullName()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bunday user yoq");
    }
}
