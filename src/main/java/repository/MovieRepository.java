/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.List;
import domain.Movie;

/**
 *
 * @author Mihailo
 */
public class MovieRepository {
    private final List<Movie> movies;
    
    public MovieRepository() {
        movies = new ArrayList<Movie>();
    }
    
    public void addMovie(Movie movie) {
        movies.add(movie);
    }
    
    public List<Movie> getAll() {
        return movies;
    }
    
    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    public void updateMovie(Movie movie) {
        for (Movie m : movies) {
            if (m.getMovieID() == movie.getMovieID()) {
                m.setName(movie.getName());
                m.setReleaseDate(movie.getReleaseDate());
                m.setDescription(movie.getDescription());
                m.setDirector(movie.getDirector());
                m.setScore(movie.getScore());
            }
        }
    }
}
