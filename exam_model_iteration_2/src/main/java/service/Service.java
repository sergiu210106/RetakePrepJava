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

    public ArrayList<Journalist> getAllJournalists() { return dbRepository.getJournalists(); }
    public ArrayList<Keyword> getAllKeywords() { return dbRepository.getKeywords(); }
    public ArrayList<Note> getAllNotes() { return dbRepository.getNotes(); }

    public ArrayList<Note> getFeed(Journalist journalist) {
        return dbRepository.getNotes().stream().filter( n -> {
            for (Keyword keyword: this.getJournalistKeywords(journalist)) {
                if (n.getText().contains(keyword.getKeyword())) {
                    return true;
                }
            }
            return false;
        }
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Keyword> getJournalistKeywords(Journalist journalist) {
        return dbRepository.getKeywords().stream().filter(k ->
                k.getjName().equals(journalist.getName())
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Note> getJournalistNotes(Journalist journalist) {
        return dbRepository.getNotes().stream().filter(note -> note.getjName().equals(journalist.getName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Keyword> filterKeywords(String filter) {
        return getAllKeywords().stream()
                .filter(k -> k.getKeyword().contains(filter))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addKeyword(Keyword keyword, Journalist journalist) {
        dbRepository.addKeyword(keyword.getKeyword(), journalist.getName());
    }
    public void addNote(String text, String time, String jName) {
        dbRepository.addNote(text, time, jName);
    }

    public void modifyNote(Note note, String text) {
        dbRepository.modifyNote(note.getId(), text);
    }
}
