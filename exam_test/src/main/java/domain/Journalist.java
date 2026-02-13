package domain;

public class Journalist {
    private String name;

    public Journalist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Journalist{" +
                "name='" + name + '\'' +
                '}';
    }
}
