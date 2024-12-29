package exercise;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> index(){

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post post){
        posts.add(post);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(post);
    }

    @GetMapping("posts/{id}")
    public ResponseEntity<Post> show(@PathVariable String id){
        Post post =  posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
        if (post != null){
            return ResponseEntity.ok().body(post);
        }return ResponseEntity.status(HttpStatus.NOT_FOUND).body(post);

    }

    @PutMapping("posts/{id}")
    public ResponseEntity<Post> update(@PathVariable String id, @RequestBody Post newPost) {
        var oldPost = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (oldPost.isPresent()) {
            var page = oldPost.get();
            page.setId(newPost.getId());
            page.setTitle(newPost.getTitle());
            page.setBody(newPost.getBody());

            return ResponseEntity.ok().body(newPost);
        }
        return ResponseEntity.noContent().build();
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
