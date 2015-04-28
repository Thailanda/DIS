package de.dis2011.data;


import java.util.Set;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class EstateAgent extends Entity {
    private int id = -1;
    private String name = "";
    private String address = "";
    private String login = "";
    private String password = "";
    private Set<Estate> estates;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Estate> getEstates() {
        return estates;
    }

    public void setEstates(Set<Estate> estates) {
        this.estates = estates;
    }
}
