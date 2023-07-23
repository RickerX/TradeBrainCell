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

    /**
     * Get ad id from RequestWrapperComment
     * @param requestWrapperComment original comment
     * @return ad id from RequestWrapperComment
     */
    public Long adsIdFromRequestWrapper(RequestWrapperComment requestWrapperComment) {
        return requestWrapperComment.getAdsId().longValue();
    }

    /**
     * Mapping RequestWrapperComment to Comment model
     * @param requestWrapperComment original comment
     * @return resulting CommentModel of type requestWrapperComment
     */
    public CommentModel commentModelFromRequestWrapper(RequestWrapperComment requestWrapperComment) {
        return mapCommentToCommentModel(requestWrapperComment.getData());
    }

    /**
     * Mapping Comment model to Comment
     * @param commentModel original model
     * @return resulting Comment
     */
    public Comment mapCommentModelToComment(CommentModel commentModel) {
        Comment comment = new Comment();
        comment.setPk(Math.toIntExact(commentModel.getId()));
        comment.setAuthor(Math.toIntExact(commentModel.getUser().getId()));
        comment.setAuthorImage(Optional.ofNullable(commentModel.getUser())
                .map(UserModel::getImage)
                .map(ImageModel::getPath)
                .orElse(null));
        comment.setAuthorFirstName(commentModel.getUser().getFirstName());

//        comment.setCreatedAt(Timestamp.valueOf(commentModel.getCreatedAt()).getTime());
//        comment.setCreatedAt(commentModel.getCreatedAt().atZone(ZoneId.of("Etc/GMT-3")).toInstant().toEpochMilli());
        comment.setCreatedAt(commentModel.getCreatedAt().toInstant(ZoneOffset.ofHours(3)).toEpochMilli());
        comment.setText(commentModel.getText());
        return comment;
    }

    /**
     * Mapping Comment  to Comment model
     * @param comment original model
     * @return resulting Comment model
     */
    public CommentModel mapCommentToCommentModel(Comment comment) {
        CommentModel commentModel = new CommentModel();
        if (null != comment.getPk()) {
            commentModel.setId(comment.getPk().longValue());
        }
        if (null != comment.getCreatedAt()) {
            commentModel.setCreatedAt(LocalDateTime.ofEpochSecond(comment.getCreatedAt(),0,ZoneOffset.ofHours(3)));
        }

//        commentModel.setId(comment.getPk().longValue());
//        commentModel.setCreatedAt(new Timestamp(comment.getCreatedAt()).toLocalDateTime());
//        commentModel.setCreatedAt(LocalDateTime.ofEpochSecond(comment.getCreatedAt(),0,ZoneOffset.ofHours(3)));
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
