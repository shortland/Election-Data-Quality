package com.electiondataquality.restservice.comments;

public class Comment {

    private int commentId;

    private String commentText;

    private int parentErrorId;

    private String parentPrecinctId;

    // TODO: TimeStamp
    // private Timestamp commentCreated;

    public Comment(int id, String text) {
        this.commentId = id;
        this.commentText = text;
        this.parentErrorId = 0;
        this.parentPrecinctId = "0";
    }

    public int getId() {
        return this.commentId;
    }

    public String getText() {
        return this.commentText;
    }

    public int getParentErrorId() {
        return this.parentErrorId;
    }

    public String getParentPrecinctId() {
        return this.parentPrecinctId;
    }

    public void updateText(String newText) {
        this.commentText = newText;
    }

    public void setParentErrorId(int parentErrorId) {
        this.parentErrorId = parentErrorId;
    }

    public void setParentPrecinctId(String parentPrecinctId) {
        this.parentPrecinctId = parentPrecinctId;
    }

    public String toString() {
        String str = Integer.toString(this.getId()) + " (" + Integer.toString(this.getParentErrorId()) + ")" + " : "
                + this.getText();

        return str;
    }
}
