package Manager;

import exceptions.MarvelException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import model.Enemy;
import model.Gem;
import model.Place;
import model.Superhero;
import model.User;
import persistence.MarvelDAO;

/**
 *
 * @author CRISTIAN
 */
public class Manager {

    public Manager() {
    }

    String green = "\033[32m";
    String purple = "\033[35m";
    String reset = "\u001B[0m";
    String yellow = "\033[33m";

    MarvelDAO marvelDAO = new MarvelDAO();
    Place place = new Place("");
    User usuario1 = new User(null, null, 0, null, place, 0);
    Superhero heroe1 = new Superhero();
    Enemy enemy1 = new Enemy();
    Gem gem1 = new Gem();
    HashMap<String, String> map = new HashMap<>();

    ArrayList<Superhero> listHeroe = new ArrayList<>();
    ArrayList<String> listNamesPlaces = new ArrayList<>();
    ArrayList<String> listPositionRanking = new ArrayList<>();
    ArrayList<Gem> listGems = new ArrayList<>();
    Random randomNumber = new Random();

    public void conectar() throws SQLException {

        marvelDAO.connect();

    }

    public void desconectar() throws SQLException {

        marvelDAO.disconnect();

    }

    /**
     * Funcion que pide al usuario datos para luego poder utilizarlos en
     * distintas Funciones
     *
     * @param mensaje
     * @return
     */
    public String pedirCadena(String mensaje) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n" + mensaje);
        try {
            return br.readLine();
        } catch (IOException ex) {
            System.out.println("Error de entrada / salida");
        }
        return "";
    }

    /**
     * Funcion que comprueba la largura de la cadena pedida al Usuario<br>
     * Mostrará el mismo error en el caso de que no sea la largura correcta<br>
     *
     * excluye el enter también
     *
     * @param comands
     * @throws MarvelException
     * @throws SQLException
     */
    public void checkLarge(String comands) throws MarvelException, SQLException {

        String datos_separados[] = comands.split(" ");

     
            
            if ((datos_separados[0].length() > 0)) {

                String letra = Character.toString(comands.charAt(0));

                switch (letra.toUpperCase()) {

                    case "R":
                        if (datos_separados.length != 4) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;
                    case "V":
                        if (datos_separados.length != 1) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;
                    case "L":
                        if (datos_separados.length != 3) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;
                    case "D":
                        if (datos_separados.length != 2) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;
                    case "G":
                        if (datos_separados.length != 3) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;
                    case "K":
                        if (datos_separados.length != 1) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;
                    case "N":
                        if (datos_separados.length != 1) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;
                    case "S":
                        if (datos_separados.length != 1) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;
                    case "O":
                        if (datos_separados.length != 1) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;
                    case "E":
                        if (datos_separados.length != 1) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;
                    case "B":
                        if (datos_separados.length != 2) {
                            throw new exceptions.MarvelException(1);
                        }
                        break;

                    case "X":
                        if (datos_separados.length != 1) {
                            throw new exceptions.MarvelException(1);
                        } else {
                            System.out.println(purple + "Bye Bye!!" + reset);
                            funtionsWithLetter("X");
                        }
                        break;

                    default:
                        throw new exceptions.MarvelException(1);
                }
            } else {
                throw new exceptions.MarvelException(1);
            }
    }

    /**
     * Funcion que selecciona la funcion que realizará segun la primera letra de
     * la cadena
     *
     * @param comands
     * @throws SQLException
     * @throws MarvelException
     */
    public void funtionsWithLetter(String comands) throws SQLException, MarvelException {

        String letra = Character.toString(comands.charAt(0));

        switch (letra.toUpperCase()) {

            case "R":
                registerUser(comands);
                gemCreation(comands);
                break;
            case "V":
                seeSuperHeroe();
                break;
            case "L":
                login(comands);
                break;
            case "D":
                deleteUser(comands);
                break;
            case "G":
                getGem(comands);
                break;
            case "K":
                seeRanking();
                break;
            case "B":
                battle(comands);
                break;
            case "N":
                move(usuario1, "N");
                break;
            case "S":
                move(usuario1, "S");
                break;
            case "E":
                move(usuario1, "E");
                break;
            case "O":
                move(usuario1, "O");
                break;
            case "X":

                break;
            default:
                break;
        }

    }

    /**
     * Esta funcion registra al usuario con los datos pasados , los separa por
     * espacios y crea un Usuario con los datos<br>
     * Posteriormente lo inserta en la base de datos mediante el metodo
     * register()
     *
     * @param comands
     */
    public void registerUser(String comands) {

        try {
            String datos_separados[] = comands.split(" ");
            String nombreUser = datos_separados[1];
            String password = datos_separados[2];
            String superHeroe = datos_separados[3];
            place = new Place("New York");

            usuario1 = new User(nombreUser, password, 1, superHeroe, place, 0);

            marvelDAO.register(usuario1);

            System.out.println("User registered.");

        } catch (SQLException | MarvelException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Función que crea 6 Gemas en sitios aleatorios<br>
     * El nombre de la gema lo coge aleatorio de un ArrayList de nombres <br>
     * El usuario lo coge del parametro introducido <br>
     * EL owner lo coge comprobando con el nombre del place si existe algun
     * villano<br>
     * El place lo coge aleatoriamente del ArrayList de nombres de Places
     * creados con otra funcion<br>
     * Una vez creada la Gema la introduce en la base de datos y borra las
     * posiciones del ArrayList de nombres de gemas para que no se repitan <br>
     * además borra el nombre del ArrayList de nombres de Places para evitar que
     * dos gemas caigan en el mismo Place
     *
     * @param comands
     * @throws SQLException
     */
    public void gemCreation(String comands) throws SQLException {

        String datos_separados[] = comands.split(" ");
        String placeDefect = "New York";
        ArrayList<String> listGemNames = new ArrayList<>(Arrays.asList("Mind Gem", "Time Gem", "Space Gem", "Soul Gem", "Reality Gem", "Power Gem"));
        listNamesPlaces = marvelDAO.returnNamePlaces();

        for (int i = 0; i < listNamesPlaces.size(); i++) {
            String namePlace = listNamesPlaces.get(i);
            if (namePlace.equalsIgnoreCase(placeDefect)) {
                listNamesPlaces.remove(i);
            }
        }

        int gemsCuantity = listGemNames.size();

        for (int i = 0; i < gemsCuantity; i++) {
            Gem gem = new Gem();
            int numero1 = randomNumber.nextInt(listGemNames.size());
            int numero2 = randomNumber.nextInt(listNamesPlaces.size());
            String nameGem = listGemNames.get(numero1);
            String namePlace = listNamesPlaces.get(numero2);

            Place newPlace = new Place(namePlace);

            Enemy enemyOwner = marvelDAO.someEnemyHereDAO(namePlace);

            if (enemyOwner != null) {

                gem = new Gem(nameGem, datos_separados[1], enemyOwner.getName(), newPlace);

            } else {
                gem = new Gem(nameGem, datos_separados[1], null, newPlace);

            }

            marvelDAO.insertarGemas(gem);

            listGemNames.remove(numero1);
            listNamesPlaces.remove(numero2);

        }

    }

    /**
     * Funcion que encuentra el usuario con la cadena pasada<br>
     * si el usuario existe muestra un mensaje de Bienvenida y llama a la
     * función welcome()
     *
     * @param comands
     * @throws SQLException
     * @throws MarvelException
     */
    public void login(String comands) throws SQLException, MarvelException {

        String datos_separados[] = comands.split(" ");
        String username = datos_separados[1];
        String pass = datos_separados[2];

        usuario1 = marvelDAO.logear(username, pass);
        System.out.println(green + "Welcome " + purple + usuario1.getUsername() + green + "!");
        welcome();

    }

    /**
     * Funcion que muestra un mensaje de bienvenida diciendole al usuario
     * logeado donde esta, las gemas y los enemigos que hay y a donde puede ir.
     *
     * @throws SQLException
     * @throws exceptions.MarvelException
     */
    public void welcome() throws SQLException, MarvelException {
        map.clear();
        listGems.clear();
        listGems = marvelDAO.returnGemsUsuario(usuario1);

        if (congratulations(listGems) == false) {

            System.out.println(green + "Place: " + purple + usuario1.getPlace().getName() + reset);
            System.out.println(yellow + usuario1.getPlace().getDescripcion() + reset);
            enemy1 = marvelDAO.someEnemyHereDAO(usuario1.getPlace().getName());
            seeEnemyorNot();

            System.out.println(green + "---" + reset);
            System.out.println(green + "You can Go:" + reset);
            map = getDirection(usuario1.getPlace());

            for (String direccion : map.keySet()) {
                System.out.print(purple + direccion + "   " + reset);
            }
        }
    }

    /**
     * Función con dos posibles opciones a imprimir en pantalla, <br>
     * 1-Si el enemy es null muestra que no hay enemigos y llama a la función
     * seeGemorNot()<br>
     * 2-Si el enemy NO es null mostrara el enemigo con su debilidad y su nivel
     *
     * @throws SQLException
     */
    public void seeEnemyorNot() throws SQLException {
        if (enemy1 == null) {
            System.out.println(green + "---" + reset);
            System.out.println(purple + "There are nobody Here" + reset);
            seeGemsorNot();
        } else {
            System.out.println(green + "- Enemies -" + reset);
            System.out.println(green + "Name: " + purple + enemy1.getName() + green + " -Debility: " + purple + enemy1.getDebility() + green + " -Level: " + purple + enemy1.getLevel() + reset);
        }
    }

    /**
     * Función que demuestra las gemas del lugar y las imprime si es que las hay
     *
     * @throws SQLException
     */
    public void seeGemsorNot() throws SQLException {
        listGems.clear();
        listGems = marvelDAO.returnGemsHereDAO(usuario1, usuario1.getPlace().getName());
        if (listGems.isEmpty()) {
            System.out.println(green + "---" + reset);
            System.out.println(purple + "There are no gems here" + reset);
        } else {
            System.out.println(green + "- Gems -" + reset);
            for (Gem gem : listGems) {
                System.out.println(purple + gem.getName() + reset);
            }
        }

    }

    /**
     * Función que te elimina un usuario si este esta logeado y escribe su
     * contraseña correctamente le pedira una comprobación de seguridad
     *
     * @param comands
     * @throws MarvelException
     * @throws SQLException
     */
    public void deleteUser(String comands) throws MarvelException, SQLException {

        String datos_separados[] = comands.split(" ");

        if (usuario1.getUsername() == null) {
            throw new exceptions.MarvelException(5);
        } else if (!datos_separados[1].equals(usuario1.getPass())) {
            throw new exceptions.MarvelException(6);
        } else {
            String confirm = "";
            while (!confirm.equalsIgnoreCase("S") && !confirm.equalsIgnoreCase("N")) {
                confirm = pedirCadena(yellow + "Are You Sure? S/N" + reset);
            }
            if (confirm.equalsIgnoreCase("S")) {
                marvelDAO.deleteUserDAO(usuario1);
                usuario1.setUsername(null);
                System.out.println(yellow + "User deleted." + reset);
            } else {
                System.out.println(yellow + "Aborted Operation." + reset);
            }
        }
    }

    /**
     * Función que primero comprueba si esta logeado el usuario<br>
     * luego crea una gema a comprobar mediante los datos pasados (g dato1
     * dato2)<br>
     * más tarde comprueba si esa gema esta dentro de un arraylist de gemas que
     * estan en ese mismo Place<br>
     * si estan y no tiene owner puede cogerla, sino debera luchar contra el
     * owner para que la gema se quede sin owner
     *
     * @param comands
     * @throws MarvelException
     * @throws SQLException
     */
    public void getGem(String comands) throws MarvelException, SQLException {

        listGems.clear();
        boolean gemObtenida = false;

        String datos_separados[] = comands.split(" ");

        if (usuario1.getUsername() == null) {
            throw new exceptions.MarvelException(5);
        } else {
            listGems = marvelDAO.returnGemsHereDAO(usuario1, usuario1.getPlace().getName());
            String nameGem = datos_separados[1] + " " + datos_separados[2];

            if (listGems.isEmpty()) {
                throw new exceptions.MarvelException(7);
            } else {

                for (Gem gem : listGems) {
                    if (gem.getName().equalsIgnoreCase(nameGem)) {

                        if (gem.getOwner() == null) {
                            marvelDAO.modifyGem(usuario1.getUsername(), gem, usuario1, enemy1, 3);
                            System.out.println(yellow + "You have got the gem" + reset);
                            gemObtenida = true;
                        } else {
                            throw new exceptions.MarvelException(8);
                        }
                    }
                }
                if (gemObtenida == false) {
                    throw new exceptions.MarvelException(7);
                }
            }
        }
        listGems = marvelDAO.returnGemsHereDAO(usuario1, usuario1.getPlace().getName());
        congratulations(listGems);
    }

    /**
     * Función que primero comprueba si algun usuario tiene gemas si es true
     * imprimira cada posicion de un arrayList ordenados según la consulta de la
     * función Ranking()
     *
     * @throws SQLException
     * @throws MarvelException
     */
    public void seeRanking() throws SQLException, MarvelException {

        if (marvelDAO.checkIfSomebodyHaveGem() == false) {
            throw new exceptions.MarvelException(9);
        } else {
            System.out.println(purple + "-----Ranking------" + reset);
            listPositionRanking = marvelDAO.ranking();
            for (String row : listPositionRanking) {
                System.out.println(row);
            }
        }
    }

    /**
     * Función que primero comprueba que el usuario este logeado<br>
     * si es asi, comprueba que el parametro pasado como enemigo(dato1) no sea
     * null o no exista en la base de datos de Enemy<br>
     * si todo es correcto, consigue el superHeroe elegido por el usuario,<br>
     * con el superHeroe comprueba la debilidad y el superpoder si són iguales
     * le sumará un ataque extra al usuario<br>
     * Posteriormente comienza el bucle de batallas con los ataques de cada
     * contrincante
     *
     * @param comands
     * @throws MarvelException
     * @throws SQLException
     */
    public void battle(String comands) throws MarvelException, SQLException {
        int atackHeroe = 0;
        int atackEnemy = 0;

        String datos_separados[] = comands.split(" ");
        if (usuario1.getUsername() == null) {
            throw new exceptions.MarvelException(5);
        } else {
            enemy1 = marvelDAO.someEnemyHereDAO(usuario1.getPlace().getName());
            if ((enemy1 == null) || !(enemy1.getName().equalsIgnoreCase(datos_separados[1]))) {
                throw new exceptions.MarvelException(10);
            } else {
                System.out.println(yellow + "\n----" + purple + "Fight Begin!" + yellow + "----" + reset);
                listHeroe = marvelDAO.seeDAOSuperHeroes();
                for (Superhero superhero : listHeroe) {
                    if (superhero.getName().equalsIgnoreCase(usuario1.getSuperhero())) {
                        heroe1 = superhero;
                    }
                }
                atackHeroe = usuario1.getLevel();
                atackEnemy = enemy1.getLevel();
                if (superPowerISDebility(heroe1.getSuperpower(), enemy1.getDebility())) {
                    atackHeroe++;
                    System.out.println(purple + "You have an " + yellow + " EXTRA" + purple + " attack!!" + yellow + "(" + usuario1.getLevel() + "+1)\n" + reset);
                }
                bucleofAtacks(atackHeroe, atackEnemy);
            }
        }
    }

    /**
     * Función que mientras el ataquedelHeroe o del Enemy no sea 0,<br>
     * muestra cuantos ataques tiene cada contrincante,<br>
     * llamara a la función lucky que devuelve un String,<br>
     * resta un ataque,<br>
     * muestra el String obtenido por la funcion lucky() .<br>
     * Luego utiliza la función fight la cual devolvera un String con el nombre
     * del ganador o draw.<br>
     * Segun el winner se imprimira diferente por pantalla y se le sumara a
     * victorias de cada fight.<br>
     * Llama a la función showvictory que mostrara el vencedor final,<br>
     * por último muestra el numero de batallas ganadas por el usuario y el
     * numero de batallas ganadas por enemigo.
     *
     * @param atackHeroe
     * @param atackEnemy
     * @throws SQLException
     */
    public void bucleofAtacks(int atackHeroe, int atackEnemy) throws SQLException {
        String winner = "";
        String luckyHeroe = "";
        String luckyEnemy = "";
        int victoryHeroe = 0;
        int victoryEnemy = 0;

        while ((atackHeroe != 0) && (atackEnemy != 0)) {

            System.out.println(green + "You " + purple + "have " + yellow + atackHeroe + purple + " attack." + reset);
            luckyHeroe = lucky();
            atackHeroe--;
            System.out.println(green + usuario1.getUsername() + purple + " attack: " + yellow + luckyHeroe + reset);

            System.out.println(green + enemy1.getName() + purple + " have " + yellow + atackEnemy + purple + " attack." + reset);
            luckyEnemy = lucky();
            atackEnemy--;
            System.out.println(green + enemy1.getName() + purple + " attack: " + yellow + luckyEnemy + reset);

            winner = fight(luckyHeroe, luckyEnemy);

            switch (winner) {
                case "Heroe":
                    victoryHeroe++;
                    System.out.println(yellow + "¡¡¡¡¡¡¡¡¡¡¡¡¡   " + green + usuario1.getUsername() + yellow + "    wins!!!!!!!!!!!!!!!" + reset);
                    break;
                case "Enemy":
                    victoryEnemy++;
                    System.out.println(yellow + "¡¡¡¡¡¡¡¡¡¡¡¡¡   " + green + enemy1.getName() + yellow + "    wins!!!!!!!!!!!!!!!" + reset);
                    break;
                case "draw":
                    System.out.println(yellow + "¡¡¡¡DRAW!!!!" + reset);
                    break;
                default:
                    break;
            }
        }
        showVictory(victoryHeroe, victoryEnemy);
        System.out.println(yellow + "----" + purple + "Fight Finished!!" + yellow + "----\n" + reset);
        System.out.println(purple + usuario1.getUsername() + yellow + " " + victoryHeroe + purple + " wins  -  " + enemy1.getName() + yellow + " " + victoryEnemy + purple + " wins." + reset);
    }

    /**
     * Función con 3 posibles opciones a realizar segun los parametros
     * pasados<br>
     * En el primer caso ha ganado el usuario y se muestra el nombre del usuario
     * y llama a la función winHeroe()<br>
     * En el segundo caso ha ganado el enemigo y se muestra el nombre del
     * enemigo y llama a la función winEnemy()<br>
     * En el último caso han quedado empate y llama a la función Draw()
     *
     * @param victoryHeroe
     * @param victoryEnemy
     * @throws SQLException
     */
    public void showVictory(int victoryHeroe, int victoryEnemy) throws SQLException {
        if (victoryHeroe > victoryEnemy) {
            System.out.println("\n" + green + usuario1.getUsername() + yellow + " wins." + reset);
            winHeroe();

        } else if (victoryHeroe < victoryEnemy) {
            System.out.println("\n" + green + enemy1.getName() + yellow + " wins." + reset);
            winEnemy();

        } else {
            System.out.println("\n" + yellow + "DRAW" + reset);
            moveEnemyRandom();
            draw();
        }
    }

    /**
     * Función que suma o resta los puntos del usuario<br>
     * Suma 5 puntos si el parametro es true(heroe ha ganado)<br>
     * Restan 2 si es false(heroe ha perdido)<br>
     * además si el resultado es menor que cero se quedara en 0 no en negativo
     *
     * @param winnerHeroe
     * @throws SQLException
     */
    public void addPoints(boolean winnerHeroe) throws SQLException {

        if (winnerHeroe) {
            usuario1.setPoints(usuario1.getPoints() + 5);
            marvelDAO.modifyPointsLevel("points");
            System.out.println(green + "You" + yellow + " win 5 points" + reset);
            System.out.println(green + "Your" + yellow + " points: " + usuario1.getPoints() + reset);
            uplevel();

        } else {
            usuario1.setPoints(usuario1.getPoints() - 2);
            if (usuario1.getPoints() < 0) {
                usuario1.setPoints(0);
            }
            marvelDAO.modifyPointsLevel("points");
            System.out.println(green + "You" + yellow + " lose 2 points" + reset);
            System.out.println(green + "Your" + yellow + " points: " + usuario1.getPoints() + reset);

        }

    }

    /**
     * Función que comprueba los puntos del usuario<br>
     * si són mayor o igual a 50 sube de nivel<br>
     * también le aumenta los puntos del nuevo nivel si han habido sobrantes del
     * anterior nivel
     *
     * @throws SQLException
     */
    public void uplevel() throws SQLException {
        if (usuario1.getPoints() >= 50) {
            usuario1.setPoints(usuario1.getPoints() - 50);
            marvelDAO.modifyPointsLevel("points");
            usuario1.setLevel(usuario1.getLevel() + 1);
            marvelDAO.modifyPointsLevel("level");
            System.out.println(green + "YOU" + yellow + " LEVEL UP!!!" + reset);
            System.out.println(green + "You level: " + yellow + usuario1.getLevel() + green + "!!!!" + reset);
            System.out.println(green + "Your points has been restored, points: " + yellow + usuario1.getPoints() + reset);

        }
    }

    /**
     * Función que modifica el owner de todas las gemas del enemigo en caso de
     * que tuviera <br>
     * Además llama a otras funciones, addPoints(añade puntos al user),<br>
     * moveEnemyRandom(mueve al enemigo a una zona aleatoria),<br>
     * luego indica que el enemigo se ha movido(disappeared)
     *
     * @throws SQLException
     */
    public void winHeroe() throws SQLException {
        listGems.clear();
        listGems = marvelDAO.returnGemsEnemy(enemy1, usuario1);
        if (!listGems.isEmpty()) {
            System.out.println(yellow + "The enemy has lost their gems!" + reset);
            for (Gem gem : listGems) {
                System.out.println(purple + gem.getName() + reset);
                marvelDAO.modifyGem("null", gem, usuario1, enemy1, 1);
            }
        }
        addPoints(true);
        moveEnemyRandom();
        System.out.println(green + enemy1.getName() + yellow + " has disappeared" + reset);

    }

    /**
     * Función que simula que gana el Enemigo <br>
     * Llama a otra función para que el enemigo cambie de Place<br>
     * Añade las gemas(si tiene) del usuario al ArrayList del enemigo<br>
     * Si no tiene muestra un mensaje de que no te pudo robar porque no tenias
     * gemas<br>
     * Si el enemigo tenia gemas te las muestra(para quedarte con las ganas) y
     * les modifica el Place el mismo que el enemigo y el owner será el enemigo
     *
     * @throws SQLException
     */
    public void winEnemy() throws SQLException {
        ArrayList<Gem> listGemsEnemy = new ArrayList<>();
        ArrayList<Gem> listGemsUsuario = new ArrayList<>();

        moveEnemyRandom();
        listGemsUsuario = marvelDAO.returnGemsUsuario(usuario1);
        listGemsEnemy = marvelDAO.returnGemsEnemy(enemy1, usuario1);

        if (!listGemsUsuario.isEmpty()) {
            System.out.println(yellow + "the enemy has stolen your gems !" + reset);
            listGemsEnemy.addAll(listGemsUsuario);
        } else {
            System.out.println(yellow + "You don't lose Gems beacause you didn't have!" + reset);
        }
        if (!listGemsEnemy.isEmpty()) {
            System.out.println(green + "Gems of Enemy:" + reset);
            for (Gem gem : listGemsEnemy) {
                System.out.println(purple + gem.getName() + reset);
                marvelDAO.modifyGem(enemy1.getPlace().getName(), gem, usuario1, enemy1, 2);
                gem.setPlace(enemy1.getPlace());
            }
        }
        addPoints(false);
        System.out.println(green + enemy1.getName() + yellow + " has disappeared" + reset);
    }

    /**
     * Función que modifica todas las gemasdel enemigo para que se vayan al
     * mismo lugar que el
     *
     * @throws SQLException
     */
    public void draw() throws SQLException {
        listGems.clear();
        listGems = marvelDAO.returnGemsEnemy(enemy1, usuario1);
        if (!listGems.isEmpty()) {
            for (Gem gem : listGems) {
                marvelDAO.modifyGem(enemy1.getPlace().getName(), gem, usuario1, enemy1, 2);
            }
            System.out.println(yellow + enemy1.getName() + " has disappeared" + reset);
        }
    }

    /**
     * Función que consigue un nombre aleatorio de una lista de nombres de Place
     * donde NO estaba ya el enemigo <br>
     * con ese nombre de Place modifica el Place del enemigo,<br>
     * luego averigua si en el nuevo Place existe una gema, <br>
     * si es así se apodera de ella (aunque tenga ya otro Owner enemigo)(son muy
     * malos los malos)
     *
     * @throws SQLException
     */
    public void moveEnemyRandom() throws SQLException {
        String nombreAleatorio = "";
        listNamesPlaces.clear();

        listNamesPlaces = marvelDAO.returnNamePlaces();
        listNamesPlaces.remove(enemy1.getPlace().getName());

        int number = randomNumber.nextInt(listNamesPlaces.size());
        nombreAleatorio = listNamesPlaces.get(number);

        enemy1.setPlace(marvelDAO.getPlacebyName(nombreAleatorio));
        marvelDAO.moveEnemy(enemy1);

        listGems.clear();
        listGems = marvelDAO.returnGemsHereDAO(usuario1, enemy1.getPlace().getName());
        if (!listGems.isEmpty()) {
            if (!listGems.isEmpty()) {
                for (Gem gem : listGems) {
                    marvelDAO.modifyGem(enemy1.getPlace().getName(), gem, usuario1, enemy1, 2);
                    System.out.println(green + gem.getName() + yellow + " gets owner " + reset);
                }
            }
        }
    }

    /**
     * Función que devuelve un String (ganador) con 3 posibles:<br>
     * posibilidades(Heroe, Enemy, Draw) segun los Strings introducidos será
     * elegido
     *
     * @param luckyHeroe
     * @param luckyEnemy
     * @return String
     */
    public String fight(String luckyHeroe, String luckyEnemy) {
        String ganador = "";

        if (luckyHeroe.equals(luckyEnemy)) {
            ganador = "draw";

        } else if (luckyHeroe.equals("Scissors") && luckyEnemy.equals("Paper") || luckyHeroe.equals("Scissors") && luckyEnemy.equals("Lizard")) {
            ganador = "Heroe";

        } else if (luckyHeroe.equals("Paper") && luckyEnemy.equals("Lizard") || luckyHeroe.equals("Paper") && luckyEnemy.equals("Rock")) {
            ganador = "Heroe";

        } else if (luckyHeroe.equals("Rock") && luckyEnemy.equals("Spock") || luckyHeroe.equals("Rock") && luckyEnemy.equals("Scissors")) {
            ganador = "Heroe";

        } else if (luckyHeroe.equals("Lizard") && luckyEnemy.equals("Spock") || luckyHeroe.equals("Lizard") && luckyEnemy.equals("Paper")) {
            ganador = "Heroe";

        } else if (luckyHeroe.equals("Spock") && luckyEnemy.equals("Scissors") || luckyHeroe.equals("Spock") && luckyEnemy.equals("Rock")) {
            ganador = "Heroe";

        } else {
            ganador = "Enemy";
        }

        return ganador;
    }

    /**
     * Funcion que te devuelve un Hasmap key(puntos cardinales) valor(con las
     * posiciones posibles a moverse)<br>
     * gracias al Place pasado como parametro<br>
     *
     *
     * @param place
     * @return ArrayList
     */
    public HashMap<String, String> getDirection(Place place) {

        if (place.getNorth() != null) {

            map.put("N", place.getNorth());
        }
        if (place.getSouth() != null) {

            map.put("S", place.getSouth());
        }
        if (place.getEast() != null) {

            map.put("E", place.getEast());
        }
        if (place.getWest() != null) {

            map.put("O", place.getWest());
        }
        return map;
    }

    /**
     * Función que actualiza el campo Place del User <br>
     * si el usuario intoduce una coordenada correctamente de las mostradas por
     * pantalla<br>
     * Si consigue moverse<br>
     * modifica el Place de usuario<br>
     * y volvera a mostrar los detalles del nuevo Place con la función welcome()
     *
     * @param user
     * @param direction
     * @throws MarvelException
     * @throws SQLException
     */
    public void move(User user, String direction) throws MarvelException, SQLException {

        if (usuario1.getUsername() == null) {
            throw new exceptions.MarvelException(5);
        } else {
            Place place = usuario1.getPlace();
            map = getDirection(place);

            if (!map.containsKey(direction)) {
                throw new exceptions.MarvelException(6);
            } else {
                Place placeToMove = marvelDAO.getPlacebyName(map.get(direction));
                usuario1.setPlace(placeToMove);
                marvelDAO.modifyPlaceOFUser(usuario1);
                System.out.println(yellow + "Moving to ..." + reset);
                welcome();
            }
        }
    }

    /**
     * Función que te devuelve un String aleatorio de dentro de un ArrayList de
     * Strings
     *
     * @return String
     */
    public String lucky() {
        String aleatoriOption = "";
        ArrayList<String> Options = new ArrayList<>(Arrays.asList("Paper", "Scissors", "Rock", "Spock", "Lizard"));
        int number = randomNumber.nextInt(Options.size());
        aleatoriOption = Options.get(number);
        return aleatoriOption;
    }

    /**
     * Funcion que muestra todos los Super Heroes de la base de Datos
     *
     * @throws SQLException
     */
    public void seeSuperHeroe() throws SQLException {

        listHeroe = marvelDAO.seeDAOSuperHeroes();

        for (Superhero heroe : listHeroe) {
            System.out.println(green + "Heroe: " + purple + heroe.getName() + green + " -SuperPower: " + purple + heroe.getSuperpower() + reset);
        }
    }

    /**
     * Función que nos devuelve true si el SuperPoder del Heroe y la Debilidad
     * del Enemigo son iguales
     *
     * @param power
     * @param debility
     * @return boolean
     */
    public boolean superPowerISDebility(String power, String debility) {

        if (power.equalsIgnoreCase(debility)) {
            return true;
        }
        return false;
    }

    /**
     * Función que nos indica que el usuario a conseguido todas las gemas si el
     * ArraydeGemas del usuario es igual a 6 al devolver un true indicara en la
     * funcion welcome() que el usuario tiene todas las gemas y que no puede
     * seguir jugando como ese usuario
     *
     * @param listGemas
     * @return boolean
     * @throws MarvelException
     * @throws SQLException
     */
    public boolean congratulations(ArrayList listGemas) throws MarvelException, SQLException {
        boolean finalofGame = false;

        if (listGemas.size() == 6) {
            System.out.println(yellow + "YOU WIN!!! YOU HAVE ALL GEMS!" + reset);
            System.out.println(yellow + "You already finish your game" + reset);
            String comand = "X";
            checkLarge(comand);
            System.out.println("If you want go out press 'X'");
            finalofGame = true;
        }
        return finalofGame;
    }

}
