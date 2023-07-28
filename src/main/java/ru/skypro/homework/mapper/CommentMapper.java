package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateComment;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class CommentMapper {
    public Comment entityToCommentDto(CommentEntity entity) {
        return new Comment(entity.getAuthor().getId(), entity.getAuthor().getImagePath(),
                entity.getAuthor().getFirstName(), getMillis(entity.getCreatedAt()),
                entity.getPk(), entity.getText());
    }

    public CommentEntity createCommentToEntity(CreateComment createComment, AdsEntity ad, UserEntity author) {
        return new CommentEntity(author, LocalDateTime.now(), createComment.getText(), ad);
    }

    private long getMillis(LocalDateTime time) {
        return time.toInstant(ZoneOffset.ofHours(5)).toEpochMilli();
    }
}
