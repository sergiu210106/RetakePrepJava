package service;

import domain.Courier;
import domain.Package;
import domain.Observer;
import domain.Subject;
import repository.DBRepository;

import java.util.ArrayList;
import java.util.Arrays;
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

    public ArrayList<Courier> getCouriers() {
        return dbRepository.getCouriers();
    }

    public ArrayList<Package> getPackages() {
        return dbRepository.getPackages();
    }

    public ArrayList<Package> getFeed(Courier courier) {
        return dbRepository.getPackages().stream()
                .filter(p -> !p.isStatus())
                .filter(p -> {
            for (String street: courier.getListStreets().split(", ")) {
                if (p.getAddress().contains(street)) return true;
            }
            return ((courier.getCenterX() - p.getX()) * (courier.getCenterX() - p.getX())
                    + (courier.getCenterY() - p.getY()) * (courier.getCenterY() - p.getY())
                    <= courier.getRadius());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Package> getOptimise(Courier courier) {
        return getFeed(courier).stream()
                .sorted(Comparator.comparing(p -> (courier.getCenterX() - p.getX()) * (courier.getCenterX() - p.getX())
                        + (courier.getCenterY() - p.getY()) * (courier.getCenterY() - p.getY())))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addPackage(String recipient, String address, String xStr, String yStr) {
        try {
            dbRepository.insertPackage(recipient, address, Float.parseFloat(xStr), Float.parseFloat(yStr));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getStreets(Courier courier) {
        return new ArrayList<>(Arrays.asList(courier.getListStreets().split(", ")));
    }

    public ArrayList<Package> filterPackages(String filter, Courier courier) {
        return getFeed(courier).stream().filter(p -> p.getAddress().contains(filter))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void deliver(Package p) {
        dbRepository.deliver(p.getRecipient());
    }

    public ArrayList<Package> getUndelivered() {
        return dbRepository.getPackages().stream().filter(p -> !p.isStatus()).collect(Collectors.toCollection(ArrayList::new));
    }
}
