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
   public class Place {
    
   private String name;
   private String descripcion;
   private String north;
   private String south;
   private String east;
   private String west;

   
    public Place(String name, String descripcion, String north, String south, String east, String west) {
        this.name = name;
        this.descripcion = descripcion;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }
   

    public Place(String name) {
        this.name = name;
    }


   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNorth() {
        return north;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    public String getEast() {
        return east;
    }

    public void setEast(String east) {
        this.east = east;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }
   
  
    
}
