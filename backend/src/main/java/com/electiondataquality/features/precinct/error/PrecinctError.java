package com.electiondataquality.features.precinct.error;

import java.util.Set;

import com.electiondataquality.features.precinct.error.enums.ERROR_TYPE;
import com.electiondataquality.jpa.tables.ErrorTable;
import com.electiondataquality.restservice.comments.Comment;

public class PrecinctError {

    private ERROR_TYPE errorType;

    private int errorId;

    private String errorText;

    private boolean isResolved;

    private Set<Comment> comments;

    private String parentPrecinctId;

    public PrecinctError(ERROR_TYPE errorType, int errorId, String errorText, boolean isResolved,
            Set<Comment> comments) {
        this.errorType = errorType;
        this.errorId = errorId;
        this.errorText = errorText;
        this.isResolved = isResolved;
        this.comments = comments;
        this.parentPrecinctId = "0";
        this.assignCommentParentId();
    }

    // Constructor for JPA
    public PrecinctError(ErrorTable et) {
        this.errorType = et.getErrorType();
        this.errorId = et.getErrorId();
        this.errorText = et.getText();
        this.isResolved = et.getResolved();
        this.parentPrecinctId = "0";
        this.comments = et.getCommentForPrecinctError();
    }

    private void assignCommentParentId() {
        for (Comment c : this.comments) {
            c.setParentErrorId(this.errorId);

            if (!this.parentPrecinctId.equals("0")) {
                c.setParentPrecinctId(this.parentPrecinctId);
            }
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

    public String getParentPrecinctId() {
        return this.parentPrecinctId;
    }

    public void setParentPrecinctId(String precinctId) {
        this.parentPrecinctId = precinctId;

        for (Comment c : this.comments) {
            c.setParentPrecinctId(precinctId);
        }
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

    public Set<Comment> getComments() {
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
