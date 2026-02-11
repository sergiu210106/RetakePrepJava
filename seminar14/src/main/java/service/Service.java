package service;

import domain.*;
import repository.DBRepository;

import java.util.ArrayList;
import java.util.Comparator;
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

    public ArrayList<Client> getClients() {
        return dbRepository.getClients();
    }

    public ArrayList<Room> getRooms() {
        return dbRepository.getRooms();
    }

    public ArrayList<Booking> getBookings() {
        return dbRepository.getBookings();
    }

    public float addBooking(int clientId, String start, String end, String type) {

        Room room = null;
        for (Room r : getRooms()) {
            boolean ok = true;
            if (r.getType().equals(type)) {
                for (Booking b : getBookings()) {
                    if (b.getRoomNumber() == r.getNumber() && (b.getEndDate().compareTo(start) > 0 && b.getStartDate().compareTo(end) < 0)) {
                        ok = false;
                        break;
                    }
                }

                if (ok) {
                    room = r;
                }
            }
        }

        if (room != null) {
            dbRepository.addBooking(clientId, room.getNumber(), start, end);
            return room.getPrice();
        }

        for (Room r : getRooms()) {
            boolean ok = true;
            for (Booking b : getBookings()) {
                if (b.getRoomNumber() == r.getNumber() && (b.getEndDate().compareTo(start) > 0 && b.getStartDate().compareTo(end) < 0)) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                dbRepository.addBooking(clientId, r.getNumber(), start, end);
                return r.getPrice();
            }
        }
        throw new RuntimeException("no room available");
    }

    public float getTotalPrice() {
        float sum = 0;
        for (Room r :  getRooms()) {
            for (Booking b : getBookings()) {
                if (r.getNumber() == b.getRoomNumber()) {
                    sum += r.getPrice();
                }
            }
        }
        return sum;
    }

    public String getSortedBookings(int number) {
        return getBookings().stream()
                .filter(b -> b.getRoomNumber()==number)
                .sorted(Comparator.comparing(Booking::getStartDate)).map(Booking::toString)
                .collect(Collectors.joining("\n"));
    }
}
