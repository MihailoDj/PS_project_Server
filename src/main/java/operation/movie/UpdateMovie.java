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
public class UpdateMovie extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.insert((MoviePoster)((Movie)param).getMoviePoster());
        repository.update((Movie)param);
        
        Role r = new Role();
        r.setMovie((Movie)param);
        repository.delete(r);
        
        for (Role role : ((Movie)param).getRoles()) {
            repository.insert(role);
        }
        
        MovieGenre mg = new MovieGenre();
        mg.setMovie((Movie)param);
        repository.delete(mg);
        
        for (MovieGenre movieGenre : ((Movie)param).getMovieGenres()) {
            repository.insert(movieGenre);
        }
        
        Production p = new Production();
        p.setMovie((Movie)param);
        repository.delete(p);
        
        for (Production production : ((Movie)param).getProductions()) {
            repository.insert(production);
        }
        
    }
    
}
