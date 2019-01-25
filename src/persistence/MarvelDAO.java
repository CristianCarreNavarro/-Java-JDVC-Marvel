/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import exceptions.MarvelException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Enemy;
import model.Gem;
import model.Place;
import model.Superhero;
import model.User;

/**
 *
 * @author CRISTIAN
 */
public class MarvelDAO {

    Connection connection;
    String green = "\033[32m";
    User usuario1 = new User("", "");
    Place place = new Place("");
    ArrayList<String> enemys = new ArrayList<>();
    Enemy enemy = new Enemy();
    Gem gem = new Gem();
    ArrayList<Superhero> listHeroe = new ArrayList<>();
    ArrayList<String> listaStrings = new ArrayList<>();
    ArrayList<Gem> listGem = new ArrayList<>();

    /**
     * Funcion que se conecta a la Base de datos seleccionada
     *
     * @throws SQLException
     */
    public void connect() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/marvel";
        String user = "root";
        String pass = "";
        connection = DriverManager.getConnection(url, user, pass);
    }

    /**
     * Funcion que se DESconecta a la Base de datos seleccionada
     *
     * @throws SQLException
     */
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    //----------------------------------------------------------USER----------------------------------------------------------------------
    /**
     * Funcion que comprueba si existe el Usuario o el Heroe y si todo va bien
     * inserta el Usuario en la base de datos
     *
     * @param usuario1
     * @throws SQLException
     * @throws MarvelException
     */
    public void register(User usuario1) throws SQLException, MarvelException {
        if (existUsuario(usuario1.getUsername(), usuario1.getPass())) {
            throw new exceptions.MarvelException(2);
        }
        if (!existHeroe(usuario1)) {
            throw new exceptions.MarvelException(3);
        }
        PreparedStatement ps = connection.prepareStatement("insert into user values (?, ?, ?, ?, ?, ?)");
        ps.setString(1, usuario1.getUsername());
        ps.setString(2, usuario1.getPass());
        ps.setInt(3, usuario1.getLevel());
        ps.setString(4, usuario1.getSuperhero());
        ps.setString(5, usuario1.getPlace().getName());
        ps.setInt(6, usuario1.getPoints());
        ps.executeUpdate();
        ps.close();

    }

    /**
     * Funcion que te crea un usuario con el mismo nombre y password de la base
     * de datos y te lo devuelve
     *
     * @param userName
     * @param pass
     * @return User
     * @throws SQLException
     * @throws MarvelException
     */
    public User logear(String userName, String pass) throws SQLException, MarvelException {

        if (!existUsuario(userName, pass)) {
            throw new exceptions.MarvelException(4);
        }
        Statement st = connection.createStatement();
        String query = "select * from user where username ='" + userName + "' and  pass='" + pass + "'";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String nameplace = (rs.getString("place"));
            place = getPlacebyName(nameplace);
            usuario1 = new User(rs.getString("username"), rs.getString("pass"), (rs.getInt("level")), rs.getString("superHero"), place, (rs.getInt("points")));
        }
        rs.close();
        st.close();
        return usuario1;

    }

    /**
     * Funcion que comprueba si existe el Usuario pasado mediante un String
     *
     * @param nombreUsuario
     * @return Boolean
     * @throws SQLException
     */
    public boolean existUsuario(String nombreUsuario, String passUsuario) throws SQLException {
        Statement st = connection.createStatement();
        String query = "select * from user where username ='" + nombreUsuario + "' and pass ='" + passUsuario + "'";
        ResultSet rs = st.executeQuery(query);

        return rs.next();
    }

    /**
     * Función que recibe un User y lo elimina de la Base de Datos
     *
     * @param user
     * @throws SQLException
     */
    public void deleteUserDAO(User user) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("delete from user where username = '" + user.getUsername() + "'");
        ps.executeUpdate();
        ps.close();

    }
/**
 * Función que puede modicar 2 cosas distintas del usuario según el parametro introducido:<br>
 * 1-points
 * 2-level
 * @param choice
 * @throws SQLException 
 */
    public void modifyPointsLevel(String choice) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("");
        switch (choice) {

            case "points":
                ps = connection.prepareStatement("update user set points=" + usuario1.getPoints() + "");
                break;

            case "level":
                ps = connection.prepareStatement("update user set level=" + usuario1.getLevel() + "");
                break;
            default:
                break;
        }
        ps.executeUpdate();
        ps.close();
    }

    //---------------------------------------------------------HEROE AND ENEMY----------------------------------------------------------------------
    /**
     * Funcion que va añadiendo Heroes en una arrayList mientras el Resultset
     * tenga Next luego devuelve el ArrayList
     *
     * @return ArrayList de Heroes
     * @throws SQLException
     */
    public ArrayList<Superhero> seeDAOSuperHeroes() throws SQLException {
        Statement st = connection.createStatement();
        String query = "select * from superhero";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Superhero s = new Superhero(rs.getString("name"), rs.getString("superpower"));
            listHeroe.add(s);
        }
        rs.close();
        st.close();
        return listHeroe;
    }

    /**
     * Funcion que te comprueba si existe el Heroe , pasandole un Usuario como
     * parametro
     *
     * @param usuario1
     * @return Boolean
     * @throws SQLException
     */
    public boolean existHeroe(User usuario1) throws SQLException {
        Statement st = connection.createStatement();
        String query = "select * from superhero where name ='" + usuario1.getSuperhero() + "'";
        ResultSet rs = st.executeQuery(query);

        return rs.next();
    }

    /**
     * Funcion que devuelve un Enemigo si existiria alguno en el Place pasado
     * como parametro
     *
     * @param place1
     * @return enemy
     * @throws SQLException
     */
    public Enemy someEnemyHereDAO(String place1) throws SQLException {

        Statement st = connection.createStatement();
        String query = "select * from enemy where place='" + place1 + "'";

        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            String nameplace = (rs.getString("place"));

            place = getPlacebyName(nameplace);
            enemy = new Enemy(rs.getString("name"), rs.getString("debility"), (rs.getInt("level")), place);
        } else {
            enemy = null;
        }
        rs.close();
        st.close();
        return enemy;
    }
/**
 * Función que  modifica el place del enemigo pasado como parametro
 * @param enemy
 * @throws SQLException 
 */
    public void moveEnemy(Enemy enemy) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("");
        ps = connection.prepareStatement("update enemy set place='" + enemy.getPlace().getName() + "' where name = '" + enemy.getName() + "'");
        ps.executeUpdate();
        ps.close();

    }

    //----------------------------------------------------------GEM----------------------------------------------------------------------
    /**
     * Función que introduce ne la base de datos la gema pasada por parametro
     *
     * @param gem
     * @throws SQLException
     */
    public void insertarGemas(Gem gem) throws SQLException {

        if (!existUsuario(usuario1.getUsername(), usuario1.getPass())) {
            PreparedStatement ps = connection.prepareStatement("insert into gem values (?, ?, ?, ?)");
            ps.setString(1, gem.getName());
            ps.setString(2, gem.getUser());
            ps.setString(3, gem.getOwner());
            ps.setString(4, gem.getPlace().getName());

            ps.executeUpdate();
            System.out.println(green + gem.getName() + "  created!!");
            ps.close();
        }
    }

    /**
     * Esta funcion devuelve una Gema si existiera alguna en el Place del
     * Usuario pasado como parametro
     *
     * @param user
     * @return Gem
     * @throws SQLException
     */
    public ArrayList<Gem> returnGemsHereDAO(User user, String placeString) throws SQLException {
        listGem.clear();
        Statement st = connection.createStatement();
        String query = "select * from gem where place ='" + placeString + "' and user='" + user.getUsername() + "'and (owner!='" + user.getUsername() + "'or owner is null)";

        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            place = new Place(rs.getString("place"));
            gem = new Gem(rs.getString("name"), rs.getString("user"), rs.getString("owner"), place);

            listGem.add(gem);
        }
        rs.close();
        st.close();
        return listGem;
    }
/**
 * Función que devuelve un ArrayList con todas las gemas donde  el usuario sea el owner
 * @param usuario
 * @return ArrayList
 * @throws SQLException 
 */
    public ArrayList<Gem> returnGemsUsuario(User usuario) throws SQLException {
        ArrayList<Gem> listGem1 = new ArrayList<>();

        Statement st = connection.createStatement();
        String query = "select * from gem where user = owner and user ='" + usuario.getUsername() + "'";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            place = new Place(rs.getString("place"));
            gem = new Gem(rs.getString("name"), rs.getString("user"), rs.getString("owner"), place);

            listGem1.add(gem);
        }
        rs.close();
        st.close();
        return listGem1;
    }
/**
 * Función muy orgulloso donde modifica de tres posibles maneras a la gema introducida por parametro:<br>
 * 1-Se modificara para que la gema simule que se deja caer<br>
 * 1-Se modificara para que el owner sea el Enemy<br>
 * 3-Se modificara para que el owner sea el usuario<br>
 *
 * @param placeString
 * @param gem
 * @param usuario
 * @param enemy
 * @param number
 * @throws SQLException 
 */
    public void modifyGem(String placeString, Gem gem, User usuario, Enemy enemy, int number) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("");
        switch (number) {
            case 1:
                ps = connection.prepareStatement("update gem set place='" + usuario1.getPlace().getName() + "', owner=" + placeString + " where name = '" + gem.getName() + "' and user='" + gem.getUser() + "'");
                break;
            case 2:
                ps = connection.prepareStatement("update gem set place='" + enemy.getPlace().getName() + "', owner='" + enemy.getName() + "' where name = '" + gem.getName() + "' and user='" + gem.getUser() + "'");
                break;
            case 3:
                ps = connection.prepareStatement("update gem set place=null, owner='" + usuario1.getUsername() + "' where name = '" + gem.getName() + "' and user='" + gem.getUser() + "'");
                break;

            default:
                break;

        }
        ps.executeUpdate();
        ps.close();

    }
/**
 * Función que devuelve true si algun usuario tiene una gema
 * @return boolean
 * @throws SQLException 
 */
    public boolean checkIfSomebodyHaveGem() throws SQLException {
        Statement st = connection.createStatement();
        String query = "select * from gem where user = owner";

        ResultSet rs = st.executeQuery(query);

        return rs.next();
    }
/**
 * Función que te devuelve las gemas del enemigo donde sea el enemigo el owner y el usuario sea el logeado
 * @param enemy
 * @param usuario
 * @return ArrayList
 * @throws SQLException 
 */
    public ArrayList<Gem> returnGemsEnemy(Enemy enemy, User usuario) throws SQLException {
        ArrayList<Gem> listGem = new ArrayList<>();
        Statement st = connection.createStatement();
        String query = "select * from gem where owner = '" + enemy.getName() + "' and user ='" + usuario.getUsername() + "'";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            place = new Place(rs.getString("place"));
            gem = new Gem(rs.getString("name"), rs.getString("user"), rs.getString("owner"), place);

            listGem.add(gem);
        }
        rs.close();
        st.close();
        return listGem;
    }

    //----------------------------------------------------------PLACE----------------------------------------------------------------------
    /**
     * Funcion que devuelve un Place segun el nombre del place pasado como
     * parametro
     *
     * @param namePlace
     * @return Place
     * @throws SQLException
     */
    public Place getPlacebyName(String namePlace) throws SQLException {

        Statement st = connection.createStatement();
        String query = "select * from place where name='" + namePlace + "'";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            place = new Place(rs.getString("name"), rs.getString("description"), rs.getString("north"), rs.getString("south"), rs.getString("east"), rs.getString("west"));
        }
        rs.close();
        st.close();
        return place;
    }

    /**
     * Función que te devuelve en un Arraylist todos los nombres de los lugares
     * de la base de datos
     *
     * @return ArrayList String
     * @throws SQLException
     */
    public ArrayList<String> returnNamePlaces() throws SQLException {
        Statement st = connection.createStatement();
        String query = "select name from place";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            String nombrePlace = (rs.getString("name"));
            listaStrings.add(nombrePlace);
        }
        rs.close();
        st.close();
        return listaStrings;
    }

    public void modifyPlaceOFUser(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update user set place='" + user.getPlace().getName() + "'where username='" + user.getUsername() + "'");
        ps.executeUpdate();
        ps.close();
    }

    //----------------------------------------------------------ESPECIAL----------------------------------------------------------------------  
    /**
     * Función que te devuelve un ArrayList de los campos seleccionados en el
     * select y los une todos en una row la cual sera introducida en el
     * ArrayList que devolvera
     *
     * @return ArrayList
     * @throws SQLException
     */
    public ArrayList<String> ranking() throws SQLException {
        String row = "";
        listaStrings.clear();

        Statement st = connection.createStatement();

        String query = " select username, superhero, count(gem.user) as gemas, level, points from user\n"
                + "inner join gem where gem.owner = user.username group by username order by count(gem.user) desc, level desc,points desc ";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {

            row = (rs.getString("username") + "    " + rs.getString("superhero") + "    " + rs.getString("gemas") + "    " + rs.getString("level") + "    " + rs.getString("points"));
            listaStrings.add(row);
        }

        rs.close();
        st.close();
        return listaStrings;
    }

}
