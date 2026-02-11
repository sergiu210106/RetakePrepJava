package ui;

import domain.Observer;
import service.Service;

public class WindowController implements Observer {
    private Service service;

    public void setService(Service service) {
        this.service = service;
        this.service.addObserver(this);
        
        //feed.setItems(feed_obs);

        update();
    }

    @Override public void update() {
        //feed_obs.setAll(service.getFeed(userName));
    }
}
