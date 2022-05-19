package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Following;

public interface FollowingRepository extends JpaRepository<Following, Integer> {
}
