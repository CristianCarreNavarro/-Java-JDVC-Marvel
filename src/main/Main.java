/*
 * To change this license header, choose License Headers in Project Properties.      
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Manager.Manager;

import exceptions.MarvelException;
import java.sql.SQLException;

/**
 *
 * @author CRISTIAN
 */
public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();
        String opcion = "";
        try {
            while (!"X".equalsIgnoreCase(opcion)) {
                try {
                    opcion = manager.pedirCadena("Introduce que desea hacer");
                    manager.conectar();

                    manager.checkLarge(opcion);

                    manager.funtionsWithLetter(opcion);

                    manager.desconectar();
                } catch (MarvelException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
