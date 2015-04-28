package de.dis2011.data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class Person extends Entity {
    private int id = -1;
    private String firstName = "";
    private String name = "";
    private String address = "";
    private Set<Contract> contracts = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public String toString() {
        return firstName + " " + name;
    }
}
