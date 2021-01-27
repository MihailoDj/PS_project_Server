/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.User;
import domain.UserStatistics;
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
public class DbUserRepository implements DbRepository<User> {

    @Override
    public List selectAll() throws Exception{
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<User> users = new ArrayList<User>();
            
            String sql = "SELECT * FROM user";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                User user = new User(); 
                user.setUserID(rs.getLong("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getString("status"));
                    
                users.add(user);
            }
            
            rs.close();
            statement.close();
            
            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DbUserRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Unable to login!");
        }
    }

    @Override
    public void insert(User user) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "INSERT INTO user (userID, username, password, status) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setLong(1, user.getUserID());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getStatus());
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
            String sql = "UPDATE user SET username=?, password=?, status=? WHERE userID=" + user.getUserID();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getStatus());
            
            
            statement.executeUpdate();
            
            statement.close();
        } catch(Exception e) {
            throw new Exception("Unable to update user!");
        }
    }

    @Override
    public List<User> select(User user) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<User> users = new ArrayList<User>();
            
            String sql = "SELECT * FROM user WHERE username=\"" + user.getUsername() + "\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getLong("userID"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setStatus(rs.getString("status"));
                        
                users.add(u);
            }
            
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(DbUserRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Connection error!");
        }
    }
    
    public List<UserStatistics> selectUserStatistics() throws Exception{
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<UserStatistics> userStats = new ArrayList<>();
            
            String sql = "SELECT u.userID, u.username, u.status, " +
                        "(SELECT COUNT(*) FROM collection c WHERE c.userID = u.userID) AS collection_size," +
                        "(SELECT COUNT(*) FROM review r WHERE r.userID = u.userID) AS review_count," +
                        "(SELECT MAX(reviewscore) AS highest_rated_movie FROM review r INNER JOIN movie m ON r.movieID=m.movieID WHERE r.userID=u.userID) AS score," +
                        "(SELECT m.name FROM movie m JOIN review r ON m.movieID=r.movieID WHERE r.userID=u.userID HAVING MAX(r.reviewscore)) AS highest_rated_movie" +
                        " FROM USER u";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                UserStatistics stats = new UserStatistics(); 
                
                User user = new User();
                user.setUserID(rs.getLong("userID"));
                user.setUsername(rs.getString("username"));
                user.setStatus(rs.getString("status"));
                stats.setUser(user);
                
                stats.setHighestRatedMovie(rs.getString("highest_rated_movie"));
                stats.setScore(rs.getDouble("score"));
                stats.setCollectionSize(rs.getInt("collection_size"));
                stats.setReviewCount(rs.getInt("review_count"));
                    
                userStats.add(stats);
            }
            
            rs.close();
            statement.close();
            
            return userStats;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DbUserRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Unable to fetch data");
        }
    }
}
