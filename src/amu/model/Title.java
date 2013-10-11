package amu.model;

import java.io.Serializable;

public class Title implements Serializable {

    private int id;
    private String name;

    public Title() {
        // Default constructor does nothing
    }
    
    public Title(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
