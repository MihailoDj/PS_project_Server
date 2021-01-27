/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.movie;

import domain.Movie;
import domain.MovieGenre;
import domain.MoviePoster;
import domain.Production;
import domain.Role;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mihailo
 */
public class InsertMovie extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Movie))
            throw new Exception("Invalid movie data!");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.insert((MoviePoster)((Movie)param).getMoviePoster());
        repository.insert((Movie)param);
        
        for (Role role : ((Movie)param).getRoles()) {
            repository.insert(role);
        }
        for (MovieGenre movieGenre : ((Movie)param).getMovieGenres()) {
            repository.insert(movieGenre);
        }
        for (Production production : ((Movie)param).getProductions()) {
            repository.insert(production);
        }
    }
    
}
