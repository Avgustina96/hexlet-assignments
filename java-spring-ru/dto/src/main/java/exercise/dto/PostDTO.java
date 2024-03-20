package exercise.dto;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Comment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDTO {
    private Long id;
    private String body;
    private String title;
    private ArrayList<CommentDTO> comments;
}
