/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author Mihailo
 */
public class DbUserRepository implements DbRepository<User>{

    @Override
    public List<User> selectAll() throws Exception{
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<User> users = new ArrayList<User>();
            
            String sql = "SELECT * FROM user";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                User user = new User() {
                    {
                        setUserID(rs.getInt("userID"));
                        setUsername(rs.getString("username"));
                        setPassword(rs.getString("password"));
                        setAdmin(rs.getBoolean("admin"));
                    }
                };
                users.add(user);
            }
            
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(DbUserRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Connection error!");
        }
    }

    @Override
    public void insert(User user) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "INSERT INTO user (userID, username, password, admin) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, user.getUserID());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.isAdmin());
            statement.executeUpdate();
            
            statement.close();
            
        } catch(Exception e) {
            throw new Exception("Username taken.");
        }
    }

    @Override
    public void delete(User user) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        String sql = "DELETE FROM user WHERE userID=" + user.getUserID();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

    @Override
    public void deleteAll() throws Exception {
        
    }

    @Override
    public void update(User user) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "UPDATE user SET username=?, password=? WHERE userID=" + user.getUserID();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            
            statement.close();
        } catch(Exception e) {
            throw new Exception("Unable to update user!");
        }
    }

    @Override
    public List<User> select(String user) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<User> users = new ArrayList<User>();
            
            String sql = "SELECT * FROM user WHERE username=\"" + user + "\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                User u = new User() {
                    {
                        setUserID(rs.getInt("userID"));
                        setUsername(rs.getString("username"));
                        setPassword(rs.getString("password"));
                        setAdmin(rs.getBoolean("admin"));
                    }
                };
                users.add(u);
            }
            
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(DbUserRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Connection error!");
        }
    }
    
}
