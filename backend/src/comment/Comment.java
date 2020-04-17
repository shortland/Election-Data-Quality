package comment;


public class Comment {
    private int commentId;
    private String commentText;

    //TODO TimeStamp 
    //private Timestamp commentCreated;

    public Comment(int id, String text){
        this.commentId = id;
        this.commentText = text;
    }

    public int getId(){
        return this.commentId;
    }

    public String getText(){
        return this.commentText;
    }
    
    public void updateText(String newText){
        this.commentText = newText;
    }

    public String toString(){
        String str = Integer.toString(this.getId()) + " : " + this.getText();
        return str;
    }
}