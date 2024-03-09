package exercise.controller.users;

import exercise.Data;
import exercise.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
// Остальные импорты

@RestController
@RequestMapping("/api")
public class PostsController {
    private List<Post> posts = Data.getPosts();

    @GetMapping("/users/{id}/posts")
    public ResponseEntity index(@PathVariable int id) {
        List<Post> userPosts = posts.stream()
                .filter(p -> p.getUserId() == id)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(userPosts);

    }


    @PostMapping("/users/{id}/posts") // Создание поста для пользователя
    public ResponseEntity create(@PathVariable int id, @RequestBody Post post) {
        post.setUserId(id);  // Устанавливаем userId из пути маршрута
        posts.add(post);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(post);
    }


}