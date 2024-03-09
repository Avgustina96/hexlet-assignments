package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/posts") // Список страниц
    public ResponseEntity<List<Post>> index(@RequestParam(defaultValue = "10") Integer limit) {
        var result = posts.stream().limit(limit).toList();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(result);
    }


    @GetMapping("/posts/{id}") // Вывод страницы
    public ResponseEntity<?> show(@PathVariable String id) {
        var page = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        try {
            page.orElseThrow();
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/posts") // Создание страницы
    public ResponseEntity<Post> create(@RequestBody Post page) {
        posts.add(page);
        var result = posts.stream();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(page);
    }


    @PutMapping("/posts/{id}") // Обновление страницы
    public ResponseEntity update(@PathVariable String id, @RequestBody Post data) {
        if (show(id).equals(data)) {
            for (int i = 0; i < posts.size(); i++) {
                if (posts.get(i).getId().equals(id)) {
                    Post page = posts.get(i);
                    page.setId(data.getId());
                    page.setBody(data.getBody());
                    page.setTitle(data.getTitle());
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
