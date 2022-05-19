package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Likes;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Integer> {

List<Likes> findAllLikesByPostId(Integer post_id);
}
