/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author CRISTIAN
 */
public class Enemy {

    private String name;
    private String debility;
    private int level;
    private Place place;

    public Enemy(String name, String debility, int level, Place place) {
        this.name = name;
        this.debility = debility;
        this.level = level;
        this.place = place;
    }

    public Enemy() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDebility() {
        return debility;
    }

    public void setDebility(String debility) {
        this.debility = debility;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}
