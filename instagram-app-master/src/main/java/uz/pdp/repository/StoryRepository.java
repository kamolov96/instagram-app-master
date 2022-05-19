package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Story;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {

    List<Story> findAllByUser_Id(Integer id);
}
