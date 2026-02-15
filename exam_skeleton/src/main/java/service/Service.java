package service;

import domain.*;
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
}
