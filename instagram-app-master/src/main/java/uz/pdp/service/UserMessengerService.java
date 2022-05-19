package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.repository.UserMessengerRepository;

@Service
public class UserMessengerService {

    @Autowired
    UserMessengerRepository userMessengerRepository;
}
