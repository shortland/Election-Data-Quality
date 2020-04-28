package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
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
import com.electiondataquality.types.errors.ErrorGen;
import com.electiondataquality.types.errors.ErrorJ;

@RestController
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
    @CrossOrigin
    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    public ErrorJ createComment(@RequestBody String commentText, @RequestParam String precinctId,
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

                return ErrorGen.ok();
            }

            return ErrorGen.create("unable to get error");
        }

        return ErrorGen.create("unable to get precinct");
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
    @CrossOrigin
    @RequestMapping(value = "/updateComment", method = RequestMethod.PUT)
    public ErrorJ updateComment(@RequestBody String commentText, @RequestParam int commentId) {
        CommentManager commentManager = RestServiceApplication.serverManager.getCommentManager();
        Comment target = commentManager.getComment(commentId);

        if (target != null) {
            target.updateText(commentText);

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to update specified comment");
    }

    /**
     * Delete a comment.
     * 
     * NOTE: Tested
     * 
     * @param commentId
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/deleteComment", method = RequestMethod.DELETE)
    public ErrorJ deleteComment(@RequestParam int commentId) {
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
                    return ErrorGen.create("unable to get parent error");
                }
            } else {
                return ErrorGen.create("unable to get parent precinct");
            }

            return ErrorGen.ok();
        }

        return ErrorGen.create("unable to delete specified comment");
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
    @CrossOrigin
    @RequestMapping(value = "/commentByError", method = RequestMethod.GET)
    public ArrayList<Comment> getAllCommentsOfError(@RequestParam int precinctId, @RequestParam int errorId) {
        CommentManager commentManager = RestServiceApplication.serverManager.getCommentManager();
        ArrayList<Comment> comments = new ArrayList<Comment>(commentManager.getCommentsByError(errorId));

        return comments;
    }
}
