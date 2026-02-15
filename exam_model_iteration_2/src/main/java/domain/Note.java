package domain;

public class Note {
    private int id;
    private String text;
    private String time;
    private String jName;

    public Note(int id, String text, String time, String jName) {
        this.id = id;
        this.text = text;
        this.time = time;
        this.jName = jName;
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

    public String getjName() {
        return jName;
    }

    public void setjName(String jName) {
        this.jName = jName;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", jName='" + jName + '\'' +
                '}';
    }
}
