package de.dis2011.model;

import de.dis2011.data.EstateAgent;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-14
 */
public class EstateAgentSecurityContext extends Observable {

    private EstateAgent user;

    public EstateAgentSecurityContext(Observer o) {
        addObserver(o);
    }

    public EstateAgent getUser() {
        return user;
    }

    public void setUser(EstateAgent user) {
        this.user = user;
        setChanged();
    }

    public boolean isAuthenticated() {
        return user != null;
    }
}
