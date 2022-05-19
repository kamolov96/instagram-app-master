package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.repository.FollowingRepository;

@Service
public class FollowingService {

    @Autowired
    FollowingRepository followingRepository;
}
