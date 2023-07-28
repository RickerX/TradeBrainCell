package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateComment;
import ru.skypro.homework.dto.ResponseWrapperComments;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.exception.FindNoEntityException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserService userService;
    private final AdsService adsService;
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;

    @Override
    public ResponseWrapperComments getComments(int id) {
        List<Comment> result = new LinkedList<>();
        commentRepository.findAllByAd_Pk(id).forEach(entity -> result.add(mapper.entityToCommentDto(entity)));
        return new ResponseWrapperComments(result.size(), result);
    }

    @Override
    public Comment add(int id, CreateComment comment, String name) {
        CommentEntity entity = mapper.createCommentToEntity(comment, adsService.getEntity(id), userService.getEntity(name));
        return mapper.entityToCommentDto(commentRepository.save(entity));
    }

    @Override
    public void delete(int commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment update(int commentId, Comment comment, String email) {
        CommentEntity entity = getEntity(commentId);
        entity.setText(comment.getText() + "(отредактировал(а) " + userService.getEntity(email).getFirstName() +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(" dd MMMM yyyy в HH:mm:ss)")));
        return mapper.entityToCommentDto(commentRepository.save(entity));
    }

    @Override
    public CommentEntity getEntity(int commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new FindNoEntityException("комментарий"));
    }
}
