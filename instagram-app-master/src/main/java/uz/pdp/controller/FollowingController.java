package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Following;
import uz.pdp.payload.ApiResponse;
import uz.pdp.repository.FollowingRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/following")
@RequiredArgsConstructor
public class FollowingController {
    final FollowingRepository followingRepository;
    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok().body(followingRepository.findAll());
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        return ResponseEntity.ok().body(followingRepository.findById(id).get());
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> unfollow(@PathVariable Integer id){
        ApiResponse apiResponse=new ApiResponse();
        Optional<Following> byId = followingRepository.findById(id);
        if (!byId.isPresent()) {
            apiResponse.setMessage("Not found");
            apiResponse.setSuccess(false);
        }
        Following following = byId.get();
        following.setActive(false);
        followingRepository.save(following);
        apiResponse.setMessage("Unfollowed");
        apiResponse.setSuccess(true);
        return ResponseEntity.status(apiResponse.isSuccess()?204:404).body(apiResponse);
    }
}
