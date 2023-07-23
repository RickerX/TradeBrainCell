package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.RequestWrapperComment;
import ru.skypro.homework.dto.ResponseWrapperComments;

public interface CommentService {

    ResponseWrapperComments getComments(RequestWrapperComment rq);


    Comment addComment(RequestWrapperComment rq);


    boolean isMine(RequestWrapperComment rq);


    Comment deleteComment(RequestWrapperComment rq);


    Comment updateComment(RequestWrapperComment rq);
}
