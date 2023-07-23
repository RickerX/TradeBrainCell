package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.RequestWrapperComment;
import ru.skypro.homework.dto.ResponseWrapperComments;

public interface CommentService {/**
 * Get comment for current ad
 * @param rq current ad
 * @return list of comments
 */
ResponseWrapperComments getComments(RequestWrapperComment rq);

    /**
     * Add new comment
     * @param rq current ad and new comment
     * @return edited comment
     */
    Comment addComment(RequestWrapperComment rq);

    /**
     * Checks if I am the owner of the comment
     * @param rq verifiable comment
     * @return true if that comment is mine
     */
    boolean isMine(RequestWrapperComment rq);

    /**
     * Delete comment
     * @param rq deleted comment
     * @return deleted comment
     */
    Comment deleteComment(RequestWrapperComment rq);

    /**
     * Update comment
     * @param rq updated comment
     * @return updated comment
     */
    Comment updateComment(RequestWrapperComment rq);
}
