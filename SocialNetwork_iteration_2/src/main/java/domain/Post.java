package domain;

public class Post {
    private int id;
    private String text;
    private String time;
    private String userName;

    public Post(int id, String text, String time, String userName) {
        this.id = id;
        this.text = text;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Post{" +
                "text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
