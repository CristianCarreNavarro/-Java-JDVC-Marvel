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
public class User {

    private String username;
    private String pass;
    private int level;
    private String superhero;
    private Place place;
    private int points;

    public User(String username, String pass, int level, String superhero, Place place, int points) {
        this.username = username;
        this.pass = pass;
        this.level = level;
        this.superhero = superhero;
        this.place = place;
        this.points = points;
    }

    public User() {

    }

    public User(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    public User(Place place) {
        this.place = place;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSuperhero() {
        return superhero;
    }

    public void setSuperhero(String superhero) {
        this.superhero = superhero;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
