/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Actor;
import java.util.List;
import domain.Movie;
import domain.User;
import domain.Director;
import domain.Genre;
import domain.ProductionCompany;
import domain.Review;
import domain.UserMovieCollection;
import repository.Repository;
import repository.db.DbRepository;
import repository.db.impl.DbActorRepository;
import repository.db.impl.DbDirectorRepository;
import repository.db.impl.DbGenreRepository;
import repository.db.impl.DbMovieRepository;
import repository.db.impl.DbProductionCompanyRepository;
import repository.db.impl.DbReviewRepository;
import repository.db.impl.DbUserMovieCollectionRepository;
import repository.db.impl.DbUserRepository;

/**
 *
 * @author Mihailo
 */
public class Controller {
    private final Repository userRepository;
    private final Repository directorRepository;
    private final Repository movieRepository;
    private final Repository actorRepository;
    private final Repository genreRepository;
    private final Repository productionCompanyRepository;
    private final Repository collectionRepository;
    private final Repository reviewRepository;
    
    private static Controller controller;

    private Controller() {
        userRepository = new DbUserRepository();
        directorRepository = new DbDirectorRepository();
        movieRepository = new DbMovieRepository();
        actorRepository = new DbActorRepository();
        genreRepository = new DbGenreRepository();
        productionCompanyRepository = new DbProductionCompanyRepository();
        collectionRepository = new DbUserMovieCollectionRepository();
        reviewRepository = new DbReviewRepository();
    }
    
    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        
        return controller;
    }
    
    public User login(String username, String password) throws Exception{
        List<User> users = userRepository.selectAll();
        
        for (User user : users) {
            if(user.getUsername().equals(username)){
                if (user.getPassword().equals(password)) {
                    return user;
                } else {
                    throw new Exception("Incorrect password!");
                }
            }
        }
        
        throw new Exception("User doesn't exist.");
    }
    
    public List<User> selectUser(String user) throws Exception{
        List<User> users = null;
        
        try {
            users = userRepository.select(user);
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return users;
    }
    
    public void updateUser(User user) throws Exception{
        ((DbRepository)userRepository).connect();
        
        try{
            ((DbRepository)userRepository).update(user);
            ((DbRepository)userRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)userRepository).rollback();
            throw e;
        }
    }
    
    public void insertUser(User user) throws Exception{
        ((DbRepository)userRepository).connect();
        
        try{
            userRepository.insert(user);
            ((DbRepository)userRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)userRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)userRepository).disconnect();
        }
    }
    
    public void deleteUser(User user) throws Exception {
        ((DbRepository)userRepository).connect();
        
        try{
            userRepository.delete(user);
            ((DbRepository)userRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)userRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)userRepository).disconnect();
        }
    }
    
    public List<Director> selectAllDirectors() throws Exception{
        List<Director> directors = null;
        
        try {
            directors = directorRepository.selectAll();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return directors;
    }
    
    public List<Movie> selectAllMovies() throws Exception {
        List<Movie> movies = null;
        
        try{
            movies = movieRepository.selectAll();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        
        return movies;
    }
    
    public List<Actor> selectAllActors() throws Exception{
        List<Actor> actors = null;
        
        try {
            actors = actorRepository.selectAll();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return actors;
    }
    
    public List<Genre> selectAllGenres() throws Exception{
        List<Genre> genres = null;
        
        try {
            genres = genreRepository.selectAll();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return genres;
    }
    
    public List<ProductionCompany> selectAllProductionCompanies() throws Exception{
        List<ProductionCompany> productionCompanies = null;
        
        try {
            productionCompanies = productionCompanyRepository.selectAll();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return productionCompanies;
    }
    
    public void insertMovie(Movie movie) throws Exception {
        ((DbRepository)movieRepository).connect();
        
        try{
            movieRepository.insert(movie);
            ((DbRepository)movieRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)movieRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)movieRepository).disconnect();
        }
    }
    
    public void deleteMovie(Movie movie) throws Exception {
        ((DbRepository)movieRepository).connect();
        
        try{
            movieRepository.delete(movie);
            ((DbRepository)movieRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)movieRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)movieRepository).disconnect();
        }
    }

    public void updateMovie(Movie movie) throws Exception {
        ((DbRepository)movieRepository).connect();
        
        try{
            ((DbRepository)movieRepository).update(movie);
            ((DbRepository)movieRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)movieRepository).rollback();
            throw e;
        }
    }
    
    public List<Movie> selectMovies(String criteria) throws Exception {
        List<Movie> movies = null;
        
        try{
            movies = movieRepository.select(criteria);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        
        return movies;
    }
    
    public void insertDirector(Director director) throws Exception {
        ((DbRepository)directorRepository).connect();
        
        try{
            directorRepository.insert(director);
            ((DbRepository)directorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)directorRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)directorRepository).disconnect();
        }
    }
    
    public void deleteDirector(Director director) throws Exception {
        ((DbRepository)directorRepository).connect();
        
        try{
            directorRepository.delete(director);
            ((DbRepository)directorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)directorRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)directorRepository).disconnect();
        }
    }

    public void updateDirector(Director director) throws Exception {
        ((DbRepository)directorRepository).connect();
        
        try{
            ((DbRepository)directorRepository).update(director);
            ((DbRepository)directorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)directorRepository).rollback();
            throw e;
        }
    }
    
    public void insertActor(Actor actor) throws Exception {
        ((DbRepository)actorRepository).connect();
        
        try{
            actorRepository.insert(actor);
            ((DbRepository)actorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)actorRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)actorRepository).disconnect();
        }
    }
    
    public void deleteActor(Actor actor) throws Exception {
        ((DbRepository)actorRepository).connect();
        
        try{
            actorRepository.delete(actor);
            ((DbRepository)actorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)actorRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)actorRepository).disconnect();
        }
    }

    public void updateActor(Actor actor) throws Exception {
        ((DbRepository)actorRepository).connect();
        
        try{
            ((DbRepository)actorRepository).update(actor);
            ((DbRepository)actorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)actorRepository).rollback();
            throw e;
        }
    }
    
    public void insertCollection(UserMovieCollection collection) throws Exception{
        ((DbRepository)collectionRepository).connect();
        
        try{
            collectionRepository.insert(collection);
            ((DbRepository)collectionRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)collectionRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)collectionRepository).disconnect();
        }
    }
    
    public List<UserMovieCollection> selectAllCollections() throws Exception{
        List<UserMovieCollection> collection = null;
        
        try {
            collection = collectionRepository.selectAll();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return collection;
    }
    
    public void deleteCollection(UserMovieCollection collection) throws Exception{
        ((DbRepository)collectionRepository).connect();
        
        try{
            collectionRepository.delete(collection);
            ((DbRepository)collectionRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)collectionRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)collectionRepository).disconnect();
        }
    }
    
    public void insertReview(Review review) throws Exception{
        ((DbRepository)reviewRepository).connect();
        
        try{
            reviewRepository.insert(review);
            ((DbRepository)reviewRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)reviewRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)reviewRepository).disconnect();
        }
    }

    public List<Review> selectAllReviews() throws Exception{
        List<Review> reviews = null;
        
        try {
            reviews = reviewRepository.selectAll();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return reviews;
    }
    
    public void deleteReview(Review review) throws Exception{
        ((DbRepository)reviewRepository).connect();
        
        try{
            reviewRepository.delete(review);
            ((DbRepository)reviewRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)reviewRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)reviewRepository).disconnect();
        }
    }
    
    public void updateReview (Review review) throws Exception {
        ((DbRepository)reviewRepository).connect();
        
        try{
            ((DbRepository)reviewRepository).update(review);
            ((DbRepository)reviewRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)reviewRepository).rollback();
            throw e;
        }
    }
}
