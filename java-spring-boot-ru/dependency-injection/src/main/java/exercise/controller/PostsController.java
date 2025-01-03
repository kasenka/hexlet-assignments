package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Post> index(){
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post show(@PathVariable long id){
        Post findPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NO POSTS WITH THIS ID"));
        return findPost;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post){
        postRepository.save(post);
        return post;
    }

    @PutMapping(path = "/{id}")
    public Post edit(@PathVariable long id, @RequestBody Post post){
        Post oldPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NO POSTS WITH THIS ID"));

        oldPost.setTitle(post.getTitle());
        oldPost.setBody(post.getBody());

        return oldPost;
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id){
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }
}
// END

