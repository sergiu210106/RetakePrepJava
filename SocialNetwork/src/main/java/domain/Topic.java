package domain;

public class Topic {
    private String topic;
    private String userName;

    public Topic (String topic, String userName) {
        this.topic = topic;
        this.userName = userName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override public String toString() {
        return topic;
    }
}
