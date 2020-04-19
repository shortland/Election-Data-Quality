package comment;

public class Comment {
    private int commentId;
    private String commentText;
    private int parentErrorId;

    // TODO TimeStamp
    // private Timestamp commentCreated;

    public Comment(int id, String text) {
        this.commentId = id;
        this.commentText = text;
        // default parentErrorId = 0
        this.parentErrorId = 0;
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

    public void updateText(String newText) {
        this.commentText = newText;
    }

    public void setParentErrorId(int parentErrorId) {
        this.parentErrorId = parentErrorId;
    }

    public String toString() {
        String str = Integer.toString(this.getId()) + " (" + Integer.toString(this.getParentErrorId()) + ")" + " : "
                + this.getText();
        return str;
    }
}