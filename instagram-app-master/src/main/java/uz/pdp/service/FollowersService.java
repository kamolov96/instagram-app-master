package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.repository.FollowersRepository;

@Service
public class FollowersService {

    @Autowired
    FollowersRepository followersRepository;
}
