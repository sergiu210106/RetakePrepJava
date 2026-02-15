package service;

import domain.*;
import repository.DBRepository;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Service implements Subject {
    private DBRepository dbRepository;
    private ArrayList<Observer> observers = new ArrayList<>();
    public Service() {
        this.dbRepository = new DBRepository();
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public ArrayList<Client> getAllClients() {
        return dbRepository.getClients();
    }

    public ArrayList<Room> getAllRooms() {
        return dbRepository.getRooms();
    }

    public ArrayList<Booking> getAllBookings() {
        return dbRepository.getBookings();
    }

    public float getTotalPrice() {
        float sum = 0;
        for (Booking b: this.getAllBookings()) {
            for (Room room : this.getAllRooms()) {
                if (room.getNumber() == b.getRoomNumber()) {
                    sum += room.getPrice();
                    break;
                }
            }
        }
        return sum;
    }

    public ArrayList<Booking> getRoomBookings(Room room) {
        return dbRepository.getBookings().stream().filter(b -> b.getRoomNumber()==room.getNumber()).collect(Collectors.toCollection(ArrayList::new));
    }


    public float addBooking(Client client, String start, String end, String type) {
        Room roomType = null;
        Room roomNonType = null;

        for (Room room: this.getAllRooms()) {
            boolean ok = true;
            for (Booking b: this.getRoomBookings(room)) {
                if ((start.compareTo(b.getEnd()) <= 0 && b.getEnd().compareTo(end) <= 0) ||
                        (start.compareTo(b.getStart()) <= 0 && b.getStart().compareTo(end) <= 0)) {
                    ok = false; break;
                }
            }
            if (ok) {
                System.out.println("ok");
                if (room.getType().equals(type)) {
                    roomType = room;
                    break;
                } else {
                    roomNonType = room;
                }
            }
        }

        if (roomType != null) {
            dbRepository.addBooking(client.getId(), roomType.getNumber(), start, end);
            return roomType.getPrice();
        } else if (roomNonType != null) {
            dbRepository.addBooking(client.getId(), roomNonType.getNumber(), start, end);
            return roomNonType.getPrice();
        } else {
            throw new RuntimeException("No rooms available in that period");
        }
    }
}
