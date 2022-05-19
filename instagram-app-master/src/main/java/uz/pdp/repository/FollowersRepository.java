package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Followers;

import java.util.List;

public interface FollowersRepository extends JpaRepository<Followers, Integer> {
    List<Followers> findAllByUser_FullNameContainingIgnoreCase(String name);
}
