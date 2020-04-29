package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.comments.Comment;
import com.electiondataquality.features.precinct.Precinct;
import com.electiondataquality.features.precinct.error.PrecinctError;
import com.electiondataquality.restservice.managers.CommentManager;
import com.electiondataquality.restservice.managers.PrecinctManager;
import com.electiondataquality.types.responses.ApiResponse;
import com.electiondataquality.types.responses.ResponseGen;
import com.electiondataquality.types.responses.enums.API_STATUS;

@RestController
@CrossOrigin
public class CommentController {

    /**
     * Create a comment.
     * 
     * NOTE: Tested
     * 
     * @param commentText
     * @param precinctId
     * @param errorId
     * @return
     */
    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    public ApiResponse createComment(@RequestBody String commentText, @RequestParam String precinctId,
            @RequestParam int errorId) {
        CommentManager commentManager = RestServiceApplication.serverManager.getCommentManager();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();

        int newId = commentManager.getLargestId() + 1;
        Comment newComment = new Comment(newId, commentText);
        Precinct parentPrecinct = precinctManager.getPrecinct(precinctId);

        if (parentPrecinct != null) {
            PrecinctError parentError = parentPrecinct.getPrecinctError(errorId);

            if (parentError != null) {
                newComment.setParentErrorId(errorId);
                newComment.setParentPrecinctId(precinctId);
                parentError.addComment(newComment);
                commentManager.addComment(newComment);

                return ResponseGen.create(API_STATUS.OK, "successfully created comment");
            }

            return ResponseGen.create(API_STATUS.ERROR, "unable to get error for the precinct");
        }

        return ResponseGen.create(API_STATUS.ERROR, "unable to get the precinct");
    }

    /**
     * Update a comment.
     * 
     * NOTE: Tested
     * 
     * @param commentText
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/updateComment", method = RequestMethod.PUT)
    public ApiResponse updateComment(@RequestBody String commentText, @RequestParam int commentId) {
        CommentManager commentManager = RestServiceApplication.serverManager.getCommentManager();
        Comment target = commentManager.getComment(commentId);

        if (target != null) {
            target.updateText(commentText);

            return ResponseGen.create(API_STATUS.OK, "successfully updated comment");
        }

        return ResponseGen.create(API_STATUS.ERROR, "unable to update specified comment");
    }

    /**
     * Delete a comment.
     * 
     * NOTE: Tested
     * 
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/deleteComment", method = RequestMethod.DELETE)
    public ApiResponse deleteComment(@RequestParam int commentId) {
        CommentManager commentManager = RestServiceApplication.serverManager.getCommentManager();
        PrecinctManager precinctManager = RestServiceApplication.serverManager.getPrecinctManager();
        Comment target = commentManager.getComment(commentId);

        if (target != null) {
            int parentErrorId = target.getParentErrorId();
            String parentPrecinctId = target.getParentPrecinctId();
            Precinct parentPrecinct = precinctManager.getPrecinct(parentPrecinctId);

            if (parentPrecinct != null) {
                PrecinctError parentError = parentPrecinct.getPrecinctError(parentErrorId);

                if (parentError != null) {
                    parentError.deleteComment(commentId);
                    commentManager.deleteComment(commentId);
                } else {
                    return ResponseGen.create(API_STATUS.ERROR, "unable to get parent error");
                }
            } else {
                return ResponseGen.create(API_STATUS.ERROR, "unable to get parent precinct");
            }

            return ResponseGen.create(API_STATUS.OK, "successfully deleted comment");
        }

        return ResponseGen.create(API_STATUS.ERROR, "unable to delete specified comment");
    }

    /**
     * Get comments by the error id.
     * 
     * NOTE: Tested
     * 
     * @param precinctId
     * @param errorId
     * @return
     */
    @RequestMapping(value = "/commentByError", method = RequestMethod.GET)
    public ApiResponse getAllCommentsOfError(@RequestParam int precinctId, @RequestParam int errorId) {
        CommentManager commentManager = RestServiceApplication.serverManager.getCommentManager();
        List<Comment> comments = new ArrayList<>(commentManager.getCommentsByError(errorId));

        return ResponseGen.create(API_STATUS.OK, comments);
    }
}
