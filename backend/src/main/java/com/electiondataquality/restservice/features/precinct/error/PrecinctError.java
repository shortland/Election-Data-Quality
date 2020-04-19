package com.electiondataquality.restservice.features.precinct.error;

import java.util.HashSet;

import com.electiondataquality.restservice.comments.Comment;

public class PrecinctError {
    private ERROR_TYPE errorType;
    private int errorId;
    private String errorText;
    private boolean isResolved;
    private HashSet<Comment> comments;

    public PrecinctError(ERROR_TYPE errorType, int errorId, String errorText, boolean isResolved,
            HashSet<Comment> comments) {
        this.errorType = errorType;
        this.errorId = errorId;
        this.errorText = errorText;
        this.isResolved = isResolved;
        this.comments = comments;
        this.assignCommentParentId();
    }

    private void assignCommentParentId() {
        for (Comment c : this.comments) {
            c.setParentErrorId(this.errorId);
        }
    }

    public ERROR_TYPE getErrorType() {
        return this.errorType;
    }

    public int getId() {
        return this.errorId;
    }

    public String getText() {
        return this.errorText;
    }

    public void setText(String errorText) {
        this.errorText = errorText;
    }

    public boolean isResolved() {
        return this.isResolved;
    }

    public void resolved() {
        this.isResolved = true;
    }

    public void unresolved() {
        this.isResolved = false;
    }

    public HashSet<Comment> getComments() {
        return this.comments;
    }

    public boolean addComment(Comment newComment) {
        return this.comments.add(newComment);
    }

    public boolean deleteComment(int commentId) {
        Comment target = null;
        for (Comment c : this.comments) {
            if (c.getId() == commentId) {
                target = c;
            }
        }
        return this.comments.remove(target);
    }

    public String toString() {
        String str = "Error ID : " + Integer.toString(this.errorId) + "\nText : " + this.errorText + "\nIs resolved : "
                + this.isResolved + "\nComments : \n";
        for (Comment c : this.comments) {
            str = str + "\t" + c.toString() + "\n";
        }
        return str;
    }
}
