package com.electiondataquality.restservice.managers;

import java.util.HashMap;
import java.util.HashSet;

import com.electiondataquality.restservice.comments.Comment;

public class CommentManager {

    private HashMap<Integer, Comment> commentMap;

    public CommentManager() {
        this.commentMap = new HashMap<Integer, Comment>();
    }

    public CommentManager(HashSet<Comment> commentSet) {
        for (Comment c : commentSet) {
            this.commentMap.put(c.getId(), c);
        }
    }

    public void populate(HashSet<Comment> commentSet) {
        this.commentMap.clear();

        for (Comment c : commentSet) {
            this.commentMap.put(c.getId(), c);
        }
    }

    public HashSet<Comment> getAllComments() {
        HashSet<Comment> allC = new HashSet<Comment>();

        for (int cid : this.commentMap.keySet()) {
            allC.add(this.commentMap.get(cid));
        }

        return allC;
    }

    public HashSet<Comment> getCommentsByError(int errorId) {
        HashSet<Comment> result = new HashSet<Comment>();

        for (int cid : this.commentMap.keySet()) {
            if (this.commentMap.get(cid).getParentErrorId() == errorId) {
                result.add(this.commentMap.get(cid));
            }
        }

        return result;
    }

    public Comment getComment(int commentId) {
        if (this.commentMap.containsKey(commentId)) {
            return this.commentMap.get(commentId);
        }

        return null;
    }

    public void deleteComment(int commentId) {
        if (this.commentMap.containsKey(commentId)) {
            commentMap.remove(commentId);
        }
    }

    public void addComment(Comment c) {
        if (!this.commentMap.containsKey(c.getId())) {
            this.commentMap.put(c.getId(), c);
        }
    }

    public void updateComment(int commentId, Comment c) {
        if (this.commentMap.containsKey(commentId)) {
            this.commentMap.remove(commentId);
            this.commentMap.put(c.getId(), c);
        }
    }

    public int getLargestId() {
        int max = 0;

        for (int id : this.commentMap.keySet()) {
            int cur = this.commentMap.get(id).getId();

            if (cur > max) {
                max = cur;
            }
        }

        return max;
    }
}
