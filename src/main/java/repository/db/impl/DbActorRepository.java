/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.Actor;
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
public class DbActorRepository implements DbRepository<Actor>{
    @Override
    public List<Actor> selectAll() throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<Actor> actors = new ArrayList<Actor>();
            
            String sql = 
                    "SELECT * FROM actor ORDER BY firstname ASC, lastname";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                Actor actor = new Actor(){
                    {
                        setActorID(rs.getInt("actorID"));
                        setFirstName(rs.getString("firstname"));
                        setLastName(rs.getString("lastname"));
                        setBiography(rs.getString("biography"));
                    }
                };
                
                actors.add(actor);
            }
            
            return actors;
        } catch (SQLException ex) {
            Logger.getLogger(DbActorRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error loading actors!");
        }
    }

    @Override
    public void insert(Actor actor) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            
            String sql = "INSERT INTO actor (firstname, lastname, biography) VALUES(?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, actor.getFirstName());
            statement.setString(2, actor.getLastName());
            statement.setString(3, actor.getBiography());
            
            statement.executeUpdate();
            
            statement.close();
        } catch(SQLException e) {
            throw new Exception("Error inserting actor!");
        }
    }

    @Override
    public void delete(Actor actor) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        String sql = "DELETE FROM actor WHERE actorID=" + actor.getActorID();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        
        statement.close();
    }

    @Override
    public void update(Actor actor) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "UPDATE actor SET actorID=?, firstname=?, lastname=?, biography=? "
                    + "WHERE actorID=" + actor.getActorID();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, actor.getActorID());
            statement.setString(2, actor.getFirstName());
            statement.setString(3, actor.getLastName());
            statement.setString(4, actor.getBiography());
            
            statement.executeUpdate();
        
        } catch(Exception ex) {
            throw new Exception("Error updating actor!");
        }
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Actor> select(String criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
