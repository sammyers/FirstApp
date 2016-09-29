package core.four.sam.firstapp.database;

/**
 * Created by Sam on 9/29/2016.
 */

public class Todo {
    private long id;
    private String text;
    private boolean isCompleted;

    public Todo(long id, String text, boolean isCompleted) {
        this.id = id;
        this.text = text;
        this.isCompleted = isCompleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
