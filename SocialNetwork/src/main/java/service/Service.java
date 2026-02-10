package service;

import domain.*;
import repository.DBRepository;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;
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
        for (Observer observer : this.observers) {
            observer.update();
        }
    }

    public ArrayList<User> getUsers() {
        return dbRepository.getUsers();
    }

    public ArrayList<Post> getFeed(String userName) {
        return dbRepository.getFeed(userName);
    }
    public ArrayList<Post> getUserPosts(String userName) {
        return dbRepository.getUserPosts(userName);
    }
    public ArrayList<Topic> getSubscriptions(String userName) {
        return dbRepository.getTopics().stream().filter(t -> t.getUserName().equals(userName))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Topic> getTopics() {
        return dbRepository.getTopics();
    }
    public void addUserTopic(String userName, String topic) {
        dbRepository.addUserTopic(userName, topic);
    }
    public void addPost(String text, String dateTime, String userName) {
        dbRepository.addPost(text, dateTime, userName);
    }
    public void modifyPostText(int id, String text) {
        dbRepository.modifyPostText(id, text);
        notifyObservers();
    }
}
