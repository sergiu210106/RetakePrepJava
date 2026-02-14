package domain;

public class Package {
    private String recipient;
    private String address;
    private float x;
    private float y;
    private boolean status;

    public Package(String recipient, String address, float x, float y, boolean status) {
        this.recipient = recipient;
        this.address = address;
        this.x = x;
        this.y = y;
        this.status = status;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Package{" +
                "recipient='" + recipient + '\'' +
                ", address='" + address + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", status=" + status +
                '}';
    }
}
