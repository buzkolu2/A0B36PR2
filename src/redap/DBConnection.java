/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redap;

/**
 *
 * @author Fragolka
 */
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Vytváří spojení s POSTGRESQL databází, spravuje její parametry
 * 
 * @author Fragolka
 */
public class DBConnection {
    private static final String CONNECTION = "jdbc:postgresql://krizik.felk.cvut.cz:5434/student_db13_39";
    private static final String USERNAME = "student_db13_39";
    private static final String PASSWORD = "db12_39";

    private java.sql.Connection conn;

    /** Creates a new instance of DatabaseConnection */
    public DBConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("org.postgresql.Driver");
        this.conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD); 
    }

    /*
     * Vrací vytvořené spojení
     */
    public java.sql.Connection getConnection(){
        return this.conn;
    }

}
