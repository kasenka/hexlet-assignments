package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public PostDTO toPostDTO(Post post){
        PostDTO postDTO = new PostDTO();

        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setBody(post.getBody());

        List<CommentDTO> comments = new ArrayList<>();

        for(Comment comment: commentRepository.findByPostId(post.getId())){
            comments.add(toCommentDTO(comment));
        }

        postDTO.setComments(comments);
        return postDTO;
    }

    public CommentDTO toCommentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setBody(comment.getBody());

        return commentDTO;
    }

    @GetMapping(path = "")
    public List<PostDTO> index(){
        List<Post> posts = postRepository.findAll();

        List<PostDTO> postsDTO = new ArrayList<>();
        for (Post post: posts){
            postsDTO.add(toPostDTO(post));
        }

        return postsDTO;
    }

    @GetMapping(path = "/{id}")
    public PostDTO show(@PathVariable long id){

        var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        return toPostDTO(post);
    }
}
// END
