package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.comments.Comment;
import com.electiondataquality.types.errors.ErrorGen;
import com.electiondataquality.types.errors.ErrorJ;

public class CommentController {
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public int createComment(@RequestBody String commentText, @RequestParam int precinctID, @RequestParam int errorId) {
        return -1;
    }

    @RequestMapping(value = "/comment", method = RequestMethod.PUT)
    public ErrorJ updateComment(@RequestBody String commentText, @RequestParam int commentId) {
        return ErrorGen.create("unable to update specified comment");
    }

    @RequestMapping(value = "/comment", method = RequestMethod.DELETE)
    public ErrorJ deleteComment(@RequestParam int commentId) {
        return ErrorGen.create("unable to delete specified comment");
    }

    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public ArrayList<Comment> getAllCommentsOfError(@RequestParam int precinctID, @RequestParam int errorId) {
        return null;
    }
}
