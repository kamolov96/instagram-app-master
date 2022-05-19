package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Attachment;
import uz.pdp.entity.Story;
import uz.pdp.entity.User;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.StoryDTO;
import uz.pdp.repository.AttachmentRepository;
import uz.pdp.repository.StoryRepository;
import uz.pdp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    @Autowired
    StoryRepository storyRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    public ApiResponse save(StoryDTO dto) {
        Story story=new Story();

        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            story.setName(dto.getName());
            story.setUser(optionalUser.get());
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(dto.getAttachmentId());

            story.setAttachment(optionalAttachment.get());
            Story save = storyRepository.save(story);
            return new ApiResponse("Added!",true,save);
        }
       return new ApiResponse("No Added!",false);
    }

    public ApiResponse getAllUserIdStory(Integer id) {
        List<Story> allByUser_id = storyRepository.findAllByUser_Id(id);
        List<Story> storyList=new ArrayList<>();
        for (Story story : allByUser_id) {
            if (story.getActive()){
                storyList.add(story);
            }
        }
        return new ApiResponse("All Story",true,storyList);
    }

    public ApiResponse delete(Integer id) {
        Optional<Story> optionalStory = storyRepository.findById(id);

        if (optionalStory.isPresent()) {
            Story story = optionalStory.get();
            story.setActive(false);
            storyRepository.save(story);
            return new ApiResponse("Delete!",true,optionalStory.get());
        }

        return new ApiResponse("Not found!",false);

    }
}
