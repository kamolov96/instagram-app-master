package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String email);
    boolean existsByUserName(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    public User getUserByUsername(@Param("userName") String userName);

}
