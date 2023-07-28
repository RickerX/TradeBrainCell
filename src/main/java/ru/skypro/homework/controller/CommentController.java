package ru.skypro.homework.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.CommentService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperComments> getComments(@PathVariable int id) {
        return ResponseEntity.ok(commentService.getComments(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable int id, @RequestBody CreateComment comment,
                                              Authentication authentication) {
        return ResponseEntity.ok(commentService.add(id, comment, authentication.getName()));
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("@commentServiceImpl.getEntity(#commentId).author.username.equals(#auth.name) " +
            "or hasAuthority('DELETE_ANY_COMMENT')")
    public ResponseEntity<?> deleteComment(@PathVariable int adId, @PathVariable int commentId, Authentication auth) {
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("@commentServiceImpl.getEntity(#commentId).author.username.equals(#auth.name) " +
            "or hasAuthority('UPDATE_ANY_COMMENT')")
    public ResponseEntity<Comment> updateComment(@PathVariable int commentId, @RequestBody Comment newComment,
                                                 Authentication auth, @PathVariable String adId) {
        return ResponseEntity.ok(commentService.update(commentId, newComment, auth.getName()));
    }
}
