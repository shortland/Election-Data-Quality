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
            if (this.commentMap.get(cid).getParentErrorId() == errorId)
                result.add(this.commentMap.get(cid));
        }
        return result;
    }
}
