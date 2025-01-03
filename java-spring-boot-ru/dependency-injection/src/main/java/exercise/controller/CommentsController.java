package exercise.controller;

import exercise.model.Post;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Comment> index(){
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment show(@RequestParam long id){
        Comment findComment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NO Comment WITH THIS ID"));
        return findComment;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment){
        commentRepository.save(comment);
        return comment;
    }

    @PutMapping(path = "/{id}")
    public Comment edit(@RequestParam long id, @RequestBody Comment comment){
        Comment oldComment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NO Comment WITH THIS ID"));

        oldComment.setBody(comment.getBody());

        return oldComment;
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@RequestParam long id){
        commentRepository.deleteById(id);
    }
}
// END
