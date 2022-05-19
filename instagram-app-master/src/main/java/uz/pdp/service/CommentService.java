package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Comment;
import uz.pdp.entity.Likes;
import uz.pdp.entity.Post;
import uz.pdp.entity.User;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.CommentDTO;
import uz.pdp.repository.CommentRepository;
import uz.pdp.repository.PostRepository;
import uz.pdp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public ApiResponse getAllCommentPostId(Integer id) {
        List<Comment> allById = commentRepository.findAllByUser_Id(id);
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : allById) {
            if (comment.getActive()) {
                commentList.add(comment);
            }
        }
        return new ApiResponse("All Comments", true, commentList);
    }


    public ApiResponse delete(Integer id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setActive(false);
            commentRepository.save(comment);
            return new ApiResponse("Delete!", true, optionalComment.get());
        }

        return new ApiResponse("No delete comment!", false);
    }

    public ApiResponse save(CommentDTO dto) {
        Comment comment = new Comment();
        Optional<Post> optionalPost = postRepository.findById(dto.getPostId());
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalPost.isPresent()) {

            if (optionalUser.isPresent()) {
                comment.setPost(optionalPost.get());
                comment.setText(dto.getText());
                comment.setUser(optionalUser.get());
                Comment save = commentRepository.save(comment);
                return new ApiResponse("Added Comment", true, save);
            }
            return new ApiResponse("No User", false);

        }
        return new ApiResponse("No post", false);

    }

    public ApiResponse addOrDeleteLike(Integer commentId, Integer userId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent()) {
            return new ApiResponse("Comments Not Found", false);
        }
        Comment editingComment = optionalComment.get();
        List<Likes> likes = editingComment.getLikes();
        for (Likes like : editingComment.getLikes()) {
            if (like.getUser().getId().equals(userId)) {
                likes.remove(like);
                editingComment.setLikes(likes);
            } else {
                likes.add(like);
                editingComment.setLikes(likes);
            }
        }
        return new ApiResponse("Like added or deleted", true);
    }
}
