package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.RequestWrapperComment;
import ru.skypro.homework.dto.ResponseWrapperComments;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.AdsModel;
import ru.skypro.homework.model.CommentModel;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentServiceImpl implements CommentService {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    public CommentServiceImpl(
            AdsRepository adsRepository,
            CommentRepository commentRepository,
            UserRepository userRepository, UserService userService,
            CommentMapper commentMapper,
            UserMapper userMapper) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseWrapperComments getComments(RequestWrapperComment rq) {
        List<CommentModel> commentList = commentRepository
                .findAllByAds_Id(commentMapper.adsIdFromRequestWrapper(rq));
        ResponseWrapperComments wrapperComment = new ResponseWrapperComments();
        wrapperComment.setCount(commentList.size());
        wrapperComment.setResults(
                commentList.stream()
                        .map(commentMapper::mapCommentModelToComment)
                        .collect(Collectors.toList())
        );
        return wrapperComment;
    }

    @Override
    public Comment addComment(RequestWrapperComment rq) {
        Long adsId = commentMapper.adsIdFromRequestWrapper(rq);
        Optional<AdsModel> adsModelOptional =
                adsRepository.findById(adsId);
        if (adsModelOptional.isEmpty()) {
            return null;
        }
        UserModel userModel = userService.getUser(rq.getUsername());
        CommentModel commentModel = commentMapper.commentModelFromRequestWrapper(rq);
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUser(userModel);
        commentModel.setAds(adsModelOptional.get());

/*        Example<CommentModel> c = Example.of(commentModel);
        boolean exists = commentRepository.exists(c);
        if (!exists) {*/

        if (null == findComment(commentModel)) {
            return commentMapper.mapCommentModelToComment(
                    commentRepository.save(commentModel)
            );
        }
        return null;
    }

    private CommentModel findComment(CommentModel commentModel) {
        return commentRepository.findDistinctFirstByTextEqualsAndUserEqualsAndAdsEquals(commentModel.getText(), commentModel.getUser(), commentModel.getAds()).orElse(null);
    }

    private CommentModel findCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public boolean isMine(RequestWrapperComment rq) {
        Long id = commentMapper.commentModelFromRequestWrapper(rq).getId();
        CommentModel commentModel = findCommentById(id);
        if (null == commentModel) {
            return false;
        }
        return commentModel.getUser().getUsername().equals(rq.getUsername());
    }


    @Override
    public Comment deleteComment(RequestWrapperComment rq) {
        Long id = commentMapper.commentModelFromRequestWrapper(rq).getId();
        CommentModel commentModel = findCommentById(id);
        if (null == commentModel) {
            return null;
        }
        commentRepository.deleteById(id);
        return commentMapper.mapCommentModelToComment(commentModel);
    }

    @Override
    public Comment updateComment(RequestWrapperComment rq) {
        Long id = commentMapper.commentModelFromRequestWrapper(rq).getId();
        CommentModel commentModel = findCommentById(id);
        if (null == commentModel) {
            return null;
        }
        if (commentMapper.commentModelFromRequestWrapper(rq).getText().equals(commentModel.getText())) {
            return null;
        }
        commentModel.setText(commentMapper.commentModelFromRequestWrapper(rq).getText());
        return commentMapper.mapCommentModelToComment(
                commentRepository.save(commentModel)
        );
    }
}
