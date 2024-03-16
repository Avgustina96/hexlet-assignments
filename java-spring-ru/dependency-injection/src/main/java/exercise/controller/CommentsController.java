package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Comment> show() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment show(@PathVariable long id) {

        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("comment with id " + id + " not found"));
        return comment;
    }
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment) {
        if (commentRepository.findAll().contains(comment)) {
            throw new ResourceNotFoundException("Product " + comment.getId() + " already exists");
        }
        return commentRepository.save(comment);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment createId(@RequestBody Comment commentData,@PathVariable long id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("comment with id " + id + " not found"));
        comment.setBody(commentData.getBody());
        return commentRepository.save(comment);
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id) {
        commentRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }
}