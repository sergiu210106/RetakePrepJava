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

    public ArrayList<User> getUsers() {
        return dbRepository.getAllUsers();
    }

    public ArrayList<String> getTopics(User user) {
        return dbRepository.getAllTopics().stream()
                .filter(t -> t.getUserName().equals(user.getName()))
                .map(Topic::getTopic)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> getAllTopics() {
        return dbRepository.getAllTopics().stream().map(Topic::getTopic).distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Post> getFeed(User user) {
        return dbRepository.getAllPostsDesc().stream().filter(p -> {
            for (String topic : getTopics(user)) {
                if (p.getText().contains("#" + topic)) return true;
            }
            return p.getText().contains("@" + user.getName());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Post> getPosts(User user) {
        return dbRepository.getAllPostsDesc().stream()
                .filter(p -> p.getUserName().equals(user.getName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> filter(String text) {
        return getAllTopics().stream().filter(t -> t.contains(text)).collect(Collectors.toCollection(ArrayList::new));
    }

    public void addTopic(String topic, String userName) {
        dbRepository.addTopic(topic, userName);
    }

    public void addPost(String text, User user) {
        dbRepository.addPost(text, user.getName());
    }
    public void modifyPost(int id, String text) {
        dbRepository.modifyPost(id, text);
    }
}
