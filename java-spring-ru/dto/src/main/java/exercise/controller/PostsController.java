package exercise.controller;

import exercise.dto.CommentDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.dto.PostDTO;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostRepository repository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public List<PostDTO> index() {
        var posts = repository.findAll();
        var result = posts.stream()
                .map(this::toDTO)
                .toList();
        var comments = commentRepository.findAll();
        result = comments.stream().map(this::toDTOComment).
                toList();
        return result;

    }

    // Чтобы сделать работу удобнее
    // И избежать дублирования
    private PostDTO toDTO(Post post) {
        var dto = new PostDTO();
        var comment = new Comment();
        dto.setId(post.getId());
        dto.setBody(post.getBody());
        dto.setTitle(post.getTitle());
//        comment.setBody(post.);
        return dto;
    }

    private PostDTO toDTOComment(Comment comment) {
        var dto = new PostDTO();
        var dtoComment = new CommentDTO();
        dtoComment.setId(comment.getId());
        dtoComment.setBody(comment.getBody());
        ArrayList<CommentDTO> comments = new ArrayList<>();
        comments.add(dtoComment);
        dto.setComments(comments);
        return dto;
    }


    @GetMapping("/{id}")
    public PostDTO indexId(@PathVariable long id) {


        var post = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        var comment = commentRepository.findByPostId(id);

        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());

        var dtoComment = new CommentDTO();
        dtoComment.setId(comment.get(0).getId());
        dtoComment.setBody(comment.get(0).getBody());
        ArrayList<CommentDTO> comments = new ArrayList<>();
        comments.add(dtoComment);
        dto.setComments(comments);
        return dto;
    }
}