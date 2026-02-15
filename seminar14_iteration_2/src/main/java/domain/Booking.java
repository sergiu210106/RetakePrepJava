package domain;

public class Booking {
    private int clientId;
    private int roomNumber;
    private String start;
    private String end;

    public Booking(int clientId, int roomNumber, String start, String end) {
        this.clientId = clientId;
        this.roomNumber = roomNumber;
        this.start = start;
        this.end = end;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "clientId=" + clientId +
                ", roomNumber=" + roomNumber +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
