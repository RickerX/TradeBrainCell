package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.RequestWrapperComment;
import ru.skypro.homework.model.CommentModel;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
@Component
public class CommentMapper {
    protected UserRepository userRepository;

    public CommentMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long adsIdFromRequestWrapper(RequestWrapperComment requestWrapperComment) {
        return requestWrapperComment.getAdsId().longValue();
    }

    public CommentModel commentModelFromRequestWrapper(RequestWrapperComment requestWrapperComment) {
        return mapCommentToCommentModel(requestWrapperComment.getData());
    }

    public Comment mapCommentModelToComment(CommentModel commentModel) {
        Comment comment = new Comment();
        comment.setPk(Math.toIntExact(commentModel.getId()));
        comment.setAuthor(Math.toIntExact(commentModel.getUser().getId()));
        comment.setAuthorImage(Optional.ofNullable(commentModel.getUser())
                .map(UserModel::getImage)
                .map(ImageModel::getPath)
                .orElse(null));
        comment.setAuthorFirstName(commentModel.getUser().getFirstName());
        comment.setCreatedAt(commentModel.getCreatedAt().toInstant(ZoneOffset.ofHours(3)).toEpochMilli());
        comment.setText(commentModel.getText());
        return comment;
    }

    public CommentModel mapCommentToCommentModel(Comment comment) {
        CommentModel commentModel = new CommentModel();
        if (null != comment.getPk()) {
            commentModel.setId(comment.getPk().longValue());
        }
        if (null != comment.getCreatedAt()) {
            commentModel.setCreatedAt(LocalDateTime.ofEpochSecond(comment.getCreatedAt(),0,ZoneOffset.ofHours(3)));
        }

        if (null != comment.getText()) {
            commentModel.setText(comment.getText());
        }

        if (null != comment.getAuthor()) {
            commentModel.setUser(userRepository
                    .findById(comment.getAuthor().longValue())
                    .orElse(null))
            ;
        }
        return commentModel;
    }
}
