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
public class DeleteMovie extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Role r = new Role();
        r.setMovie((Movie)param);
        repository.delete(r);
        
        MovieGenre mg = new MovieGenre();
        mg.setMovie((Movie)param);
        repository.delete(mg);
        
        Production p = new Production();
        p.setMovie((Movie)param);
        repository.delete(p);
        
        repository.delete((Movie)param);
        repository.delete((MoviePoster)((Movie)param).getMoviePoster());
    }
    
}
