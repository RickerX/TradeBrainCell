package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateComment;
import ru.skypro.homework.dto.ResponseWrapperComments;
import ru.skypro.homework.entity.CommentEntity;

public interface CommentService {
    ResponseWrapperComments getComments(int id);

    Comment add(int id, CreateComment comment, String name);

    void delete(int commentId);

    Comment update(int commentId, Comment newComment, String email);

    CommentEntity getEntity(int commentId);
}
