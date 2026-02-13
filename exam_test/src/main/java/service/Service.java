package service;

import domain.*;
import repository.DBRepository;

import java.security.Key;
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

    public ArrayList<String> getKeywords(Journalist journalist) {
        return dbRepository.getKeywords().stream().filter(k->k.getjName().equals(journalist.getName()))
                .map(Keyword::getKeyword).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> getAllKeywords() {
        return dbRepository.getKeywords().stream().map(Keyword::getKeyword).distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Note> getFeed(Journalist journalist) {
        return dbRepository.getNotesDesc().stream().filter(p -> {
            for (String keyword : this.getKeywords(journalist)) {
                if (p.getText().contains("#" + keyword)) return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Journalist> getJournalists() {
        return dbRepository.getJournalists();
    }

    public ArrayList<Note> getNotes(Journalist journalist) {
        return dbRepository.getNotesDesc().stream()
                .filter(n -> n.getjName().equals(journalist.getName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> filterKeywords(String filter, Journalist journalist) {
        return getKeywords(journalist).stream().filter(k->k.contains(filter)).collect(Collectors.toCollection(ArrayList::new));
    }

    public void addKeyword(String name, String keyword) {
        dbRepository.addKeyword(name, keyword);
    }

    public void addNote(String text, String name) {
        dbRepository.addNote(text, name);
    }
    public void modifyNote(int id, String text) {
        dbRepository.modifyNote(id, text);
    }
}
