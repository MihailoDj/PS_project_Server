/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.genre;

import domain.Genre;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mihailo
 */
public class SelectAllGenres extends AbstractGenericOperation{
    List<Genre> genres = new ArrayList<>();
    
    @Override
    protected void preconditions(Object param) throws Exception {
        
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.selectAll((Genre)param);
        
        while (rs.next()) {
            Genre genre = new Genre();
            genre.setGenreID(rs.getLong("genreID"));
            genre.setName(rs.getString("name"));

            genres.add(genre);
            }
    }

    public List<Genre> getGenres() {
        return genres;
    }
    
    
}
