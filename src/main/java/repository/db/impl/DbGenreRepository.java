/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.Genre;
import java.sql.Connection;
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
public class DbGenreRepository implements DbRepository{

    @Override
    public void insert(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Genre> select(String obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Genre> selectAll() throws Exception {
        try{
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<Genre> genres = new ArrayList<Genre>();
            
            String sql = "SELECT * FROM genre ORDER BY name ASC";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                Genre genre = new Genre() {
                    {
                        setGenreID(rs.getInt("genreID"));
                        setName(rs.getString("name"));
                    }
                };
                genres.add(genre);
            }
            
            return genres;
        } catch (SQLException ex) {
            Logger.getLogger(DbGenreRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error loading genres!");
        }
    }
    
}
