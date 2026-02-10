package domain;
import domain.Observer;

public interface Subject {
    void addObserver(Observer observer);
    void notifyObservers();
}
