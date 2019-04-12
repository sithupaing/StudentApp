/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Sithu
 */
public class Database {
    
    private String url = "jdbc:mysql://localhost:3306/studentapp";
    private String username = "root";
    private String password = "";
    
    private static Database db;
    
    private Connection conn;
    
    private Database() throws SQLException{
        connect();
    }
    
    public static Database getInstance() throws SQLException{
        if(db==null){
            db = new Database();
        }
        return db;
    }
    
    private boolean connect() throws SQLException{
       conn =  DriverManager.getConnection(url, username, password);
       return true;
    }
    
    public Connection getConnection(){
        return conn;
    }
    
}
