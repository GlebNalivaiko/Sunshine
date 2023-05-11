package by.sunshine.converter;

import by.sunshine.dto.CommentDto;
import by.sunshine.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class CommentConverter {
    public Comment convert(CommentDto commentDto, String username) {
        Comment comment = new Comment();
        comment.setOpinion(commentDto.getOpinion().trim());
        comment.setNameOfUser(username);
        comment.setDate(LocalDate.now());
        int stars = commentDto.getStars();
        if (commentDto.getStars() > 5) stars = 5;
        else if (commentDto.getStars() < 1) stars = 1;
        comment.setStars(stars);
        return comment;
    }
}
