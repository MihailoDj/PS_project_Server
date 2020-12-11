/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.Director;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
public class DbDirectorRepository implements DbRepository<Director>{

    @Override
    public List<Director> selectAll() throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<Director> directors = new ArrayList<Director>();
            
            String sql = 
                    "SELECT * FROM director ORDER BY firstname ASC, lastname";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                Director director = new Director(){
                    {
                        setDirectorID(rs.getInt("directorID"));
                        setFirstName(rs.getString("firstname"));
                        setLastName(rs.getString("lastname"));
                        setDateOfBirth(rs.getObject("dateofbirth", LocalDate.class));
                    }
                };
                
                directors.add(director);
            }
            
            return directors;
        } catch (SQLException ex) {
            Logger.getLogger(DbDirectorRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error loading directors!");
        }
    }

    @Override
    public void insert(Director director) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            
            String sql = "INSERT INTO director (firstname, lastname, dateofbirth) VALUES(?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            statement.setObject(3, director.getDateOfBirth(), java.sql.Types.DATE);
            
            statement.executeUpdate();
            
            statement.close();
        } catch(SQLException e) {
            throw new Exception("Error inserting director!");
        }
    }

    @Override
    public void delete(Director director) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        String sql = "DELETE FROM director WHERE directorID=" + director.getDirectorID();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        
        statement.close();
    }

    @Override
    public void update(Director director) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "UPDATE director SET directorID=?, firstname=?, lastname=?, dateofbirth=? "
                    + "WHERE directorID=" + director.getDirectorID();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, director.getDirectorID());
            statement.setString(2, director.getFirstName());
            statement.setString(3, director.getLastName());
            statement.setObject(4, director.getDateOfBirth(), java.sql.Types.DATE);
            
            statement.executeUpdate();
        
        } catch(Exception ex) {
            throw new Exception("Error updating director!");
        }
    }


    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Director> select(String criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
