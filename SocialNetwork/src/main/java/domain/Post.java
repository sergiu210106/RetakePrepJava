package domain;

public class Post {
    private int id;
    private String text;
    private String dateTime;
    private String userName;

    public Post(int id, String text, String dateTime, String userName) {
        this.id = id;
        this.text = text;
        this.dateTime = dateTime;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s : %s", dateTime, userName, text);
    }
}
