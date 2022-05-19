package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.CommentDTO;
import uz.pdp.service.CommentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/post/{postId}")
    public HttpEntity<?> getAllCommentPostId(@PathVariable Integer postId){
        ApiResponse response=commentService.getAllCommentPostId(postId);
        return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse response=commentService.delete(id);
        return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(response);

    }

    @PostMapping
    public HttpEntity<?> save(@RequestBody CommentDTO commentDTO){
        ApiResponse response=commentService.save(commentDTO);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }

    @PutMapping("/likesForComment/{commentId}")
    public HttpEntity<?> addOrDeleteLike(@PathVariable Integer commentId, @RequestParam Integer userId) {
        ApiResponse response = commentService.addOrDeleteLike(commentId, userId);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }


    //--------------------------VALIDATION-----------------------
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
