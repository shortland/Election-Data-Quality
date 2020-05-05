package com.electiondataquality.jpa.tables;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class CommentTable {

    @Id
    @Column(name = "idn")
    private int commentId;

    @Column(name = "error_idn", insertable = false, updatable = false)
    private int errorId;

    @Column(name = "text")
    private String text;

    @Column(name = "created")
    private Date created;

    @Column(name = "valid")
    private int valid;

    public CommentTable() {
    }

    public CommentTable(int commentId, int errorId, String commentText, Date created, int valid) {
        this.commentId = commentId;
        this.errorId = errorId;
        this.text = commentText;
        this.created = created;
        this.valid = valid;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "CommentTable [commentId=" + commentId + ", created=" + created + ", errorId=" + errorId + ", text="
                + text + ", valid=" + valid + "]";
    }
}
