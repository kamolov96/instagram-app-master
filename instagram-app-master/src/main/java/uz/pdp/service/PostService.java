package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Attachment;
import uz.pdp.entity.Likes;
import uz.pdp.entity.Post;
import uz.pdp.entity.User;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.PostDTO;
import uz.pdp.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    LikesRepository likesRepository;


    public ApiResponse save(PostDTO postDTO) {

        Post post = new Post();
        Optional<User> optionalUser = userRepository.findById(postDTO.getUserId());

        if (optionalUser.isPresent()) {
            post.setUser(optionalUser.get());
            post.setDescription(postDTO.getDescription());
            List<Attachment> allById = attachmentRepository.findAllById(postDTO.getAttachmentIds());
            post.setAttachments(allById);
//            List<Comment> allById1 = commentRepository.findAllById(postDTO.getCommentIds());
//            post.setComments(allById1);
//            List<Likes> allById2 = likesRepository.findAllById(postDTO.getLikeIds());
//            post.setLikes(allById2);
            Post save = postRepository.save(post);
            return new ApiResponse("Added!", true, save);
        }
        return new ApiResponse("No Added!",false);
    }


    public ApiResponse getOne(Integer id) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isPresent() && byId.get().getActive()) {
            return new ApiResponse("One Post", true, byId.get());
        }
        return new ApiResponse("No content",false);
    }


    public ApiResponse edit(Integer id, PostDTO dto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            Optional<User> optionalUser = userRepository.findById(dto.getUserId());
          if (optionalUser.isPresent()){
//              List<Likes> likesList = likesRepository.findAllById(dto.getLikeIds());
//              post.setLikes(likesList);
//              List<Comment> commentList = commentRepository.findAllById(dto.getCommentIds());
//              post.setComments(commentList);
              post.setDescription(dto.getDescription());
              List<Attachment> attachmentList = attachmentRepository.findAllById(dto.getAttachmentIds());
              post.setAttachments(attachmentList);
              Post save = postRepository.save(post);
              return new ApiResponse("Edit!",true,save);
          }
          return new ApiResponse("No User",false);
        }
        return new ApiResponse("No Post",false);
    }

    public ApiResponse getAll() {
        List<Post> postList=new ArrayList<>();

        for (Post post : postRepository.findAll()) {
            if (post.getActive()){
                postList.add(post);
            }
        }
        return new ApiResponse("Get All Post",true,postList);
    }

    public ApiResponse delete(Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            post.setActive(false);
          postRepository.save(post);
            return new ApiResponse("Delete!",true,optionalPost.get());
        }
        return new ApiResponse("Not found!",false);
    }

    public ApiResponse getUserIdPost(Integer id) {

        List<Post> allUserIdPost = postRepository.findAllByUser_Id(id);
        List<Post> postList=new ArrayList<>();
        for (Post post : allUserIdPost) {
            if (post.getActive()){
                postList.add(post);
            }
        }
        return new ApiResponse("All Post",true,postList);
    }

    public ApiResponse addOrDeleteLike(Integer postId, Integer userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            return new ApiResponse("Not found post",false);
        }
        Post editingPost = optionalPost.get();
        List<Likes> likeList = editingPost.getLikes();
        for (Likes like : editingPost.getLikes()) {
            if (like.getUser().getId().equals(userId)) {
                likeList.remove(like);
                editingPost.setLikes(likeList);
            }
            else {
                likeList.add(like);
                editingPost.setLikes(likeList);
            }
        }
        return new ApiResponse("Like added or deleted", true);
    }
}
