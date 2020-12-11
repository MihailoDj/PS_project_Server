/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Mihailo
 */
public class DbConnectionFactory {
    private Connection connection;
    private static DbConnectionFactory instance;
    
    private DbConnectionFactory() {
        
    }
    
    public static DbConnectionFactory getInstance() {
        if (instance == null)
            instance = new DbConnectionFactory();
        return instance;
    }
    
    public Connection getConnection() throws Exception{
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:mysql://localhost:3306/psdb";
            String username = "root";
            String password = "";
            
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }
}
