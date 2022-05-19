package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.entity.User;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.UserDTO;
import uz.pdp.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ApiResponse edit(Integer id, UserDTO dto) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setEmail(dto.getEmail());
            user.setGender(dto.getGender());
            user.setPassword(dto.getPassword());
            user.setFullName(dto.getFullName());
            user.setUserName(dto.getUsername());
            user.setBio(dto.getBio());
            user.setWebsite(dto.getWebsite());
            userRepository.save(user);
            return new ApiResponse("Edited",true);
        }
        return new ApiResponse("Not user",false);
    }


}
