package domain;

public class Room {
    private int number;
    private String type;
    private String description;
    private float price;

    public Room(int number, String type, String description, float price) {
        this.number = number;
        this.type = type;
        this.description = description;
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
