package domain;

public interface Subject {
    void addObserver(Observer observer);
    void notifyObservers();
}
