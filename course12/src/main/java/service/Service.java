package service;

import domain.Courier;
import domain.Observer;
import domain.Subject;
import repository.DBRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Math.sqrt;
import domain.Package;

public class Service implements Subject {
    private DBRepository dbRepository;
    private ArrayList<Observer> observers = new ArrayList<>();
    public Service() {
        this.dbRepository = new DBRepository();
    }
    public ArrayList<Courier> getCouriers() {
        return dbRepository.getAllCouriers();
    }
    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update();
        }
    }
    private float dist(float x1, float y1, float x2, float y2) {
        return (float) sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public ArrayList<Package> getPackages(Courier courier) {
        return dbRepository.getPackages().stream().filter(p -> {
            if (courier.getListOfStreets().contains(p.getAddress().split(", ")[0])) return true;
            return dist(p.getX(), p.getY(), courier.getCentreX(), courier.getCentreY()) <= courier.getRadius();
        })
                .filter(p -> !p.getStatus())
                .sorted(Comparator.comparing(p -> dist(p.getX(), p.getY(), courier.getCentreX(), courier.getCentreY())))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Package> getAllPackages() {
        return dbRepository.getPackages();
    }

    public void addPackage(String recipient, String address, float x, float y) {
        dbRepository.addPackage(recipient, address, x, y);
    }

    public ArrayList<String> getStreets(Courier courier) {
        return new ArrayList<>(Arrays.asList(courier.getListOfStreets().split(", ")));
    }

    public String getStreetPackages(String street) {
        if (street == null) return "";
        return dbRepository.getPackages().stream()
                .filter(p -> p.getAddress().contains(street))
                .map(Package::toString)
                .collect(Collectors.joining("\n"));
    }

    public void deliver(Package p) {
        dbRepository.deliver(p.getRecipient(), p.getAddress());
    }
}
