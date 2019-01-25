/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author CRISTIAN
 */
public class MarvelException extends Exception {

    private int codigoError;
    String red = "\033[31m";
    String reset = "\u001B[0m";

    public MarvelException(int codigoError) {
        super();
        this.codigoError = codigoError;
    }

    @Override
    public String getMessage() {
        String mensaje = "";
        switch (codigoError) {

            case 1:
                mensaje = red + "<Wrong number of arguments>" + reset;
                break;
            case 2:
                mensaje = red + "<User already exists>" + reset;
                break;
            case 3:
                mensaje = red + "<There isn't a superhero with that name>" + reset;
                break;
            case 4:
                mensaje = red + "<Username or password is incorrect>" + reset;
                break;
            case 5:
                mensaje = red + "<You are not logged in>" + reset;
                break;
            case 6:
                mensaje = red + "<You can't move in that direction>" + reset;
                break;
            case 7:
                mensaje = red + "<Here there is no gem with that name>" + reset;
                break;
            case 8:
                mensaje = red + "<You have to Battle! This gem have Owner.>" + reset;
                break;
            case 9:
                mensaje = red + "<There are no users for the ranking (nobody has gems)>" + reset;
                break;
            case 10:
                mensaje = red + "<Here there is no enemy with that name>" + reset;
                break;

        }
        return mensaje;
    }

}
