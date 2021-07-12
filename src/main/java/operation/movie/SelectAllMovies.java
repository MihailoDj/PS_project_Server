/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.movie;

import domain.Actor;
import domain.Director;
import domain.Genre;
import domain.Movie;
import domain.MovieGenre;
import domain.MoviePoster;
import domain.Production;
import domain.ProductionCompany;
import domain.Role;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mihailo
 */
public class SelectAllMovies extends AbstractGenericOperation{
    private List<Movie> movies = new ArrayList<>();
    
    @Override
    protected void preconditions(Object param) throws Exception {
        
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rsMovie = repository.selectAll((Movie)param);
        
        while (rsMovie.next()) {
            Movie movie = loadMovie(rsMovie);
            
            Role r = new Role();
            r.setMovie(movie);
            ResultSet rsRoles = repository.selectAll(r);
            
            while (rsRoles.next()) {
                Actor actor = new Actor();
                actor.setActorID(rsRoles.getLong("actorID"));
                actor.setFirstName(rsRoles.getString("firstname"));
                actor.setLastName(rsRoles.getString("lastname"));
                actor.setBiography(rsRoles.getString("biography"));

                Role role = new Role();
                role.setRoleName(rsRoles.getString("rolename"));
                role.setActor(actor);
                
                movie.getRoles().add(role);
            }
            
            MovieGenre mg = new MovieGenre();
            mg.setMovie(movie);
            ResultSet rsMovieGenres = repository.selectAll(mg);
            
            while (rsMovieGenres.next()) {
                Genre genre = new Genre();
                genre.setGenreID(rsMovieGenres.getLong("ggenreID"));
                genre.setName(rsMovieGenres.getString("gname"));

                MovieGenre movieGenre = new MovieGenre();
                movieGenre.setGenre(genre);
                
                movie.getMovieGenres().add(movieGenre);
            }
            
            Production p = new Production();
            p.setMovie(movie);
            ResultSet rsProduction = repository.selectAll(p);
            
            while (rsProduction.next()) {
                ProductionCompany pc = new ProductionCompany();
                pc.setProductionCompanyID(rsProduction.getLong("pcID"));
                pc.setName(rsProduction.getString("pcname"));

                Production production = new Production();
                production.setProductionCompany(pc);
                
                movie.getProductions().add(production);
            }
            
            movies.add(movie);
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }
    
    private Movie loadMovie(ResultSet rsMovie) throws Exception{
        Director director = new Director();
        director.setDirectorID(rsMovie.getLong("directorID"));
        director.setFirstName(rsMovie.getString("firstname"));
        director.setLastName(rsMovie.getString("lastname"));
        director.setDateOfBirth(rsMovie.getObject("dateofbirth", LocalDate.class));

        MoviePoster moviePoster = new MoviePoster();
        moviePoster.setMoviePosterID(rsMovie.getLong("movieposterID"));
        moviePoster.setPosterImage(ImageIO.read(rsMovie.getBlob("posterimage").getBinaryStream()));

        Movie movie = new Movie();
        movie.setMovieID(rsMovie.getLong("movieID"));
        movie.setName(rsMovie.getString("name"));
        movie.setReleaseDate(rsMovie.getObject("releaseDate", LocalDate.class));
        movie.setDescription(rsMovie.getString("description"));
        movie.setScore(Math.floor(rsMovie.getDouble("score")* 100) / 100);
        movie.setDirector(director);
        movie.setMoviePoster(moviePoster);
        movie.setDuration(rsMovie.getInt("duration"));
        
        return movie;
    }
    
}
