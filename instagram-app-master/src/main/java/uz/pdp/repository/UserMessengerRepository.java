package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.entity.UserMessenger;

@Repository
public interface UserMessengerRepository extends JpaRepository<UserMessenger,Integer> {

}
