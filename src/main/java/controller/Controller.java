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
import domain.UserStatistics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import operation.AbstractGenericOperation;
import operation.actor.DeleteActor;
import operation.actor.InsertActor;
import operation.actor.SelectAllActors;
import operation.actor.genre.SelectAllGenres;
import operation.actor.productionCompany.SelectAllProductionCompanies;
import operation.director.DeleteDirector;
import operation.director.InsertDirector;
import operation.director.SelectAllDirectors;
import operation.director.UpdateDirector;
import operation.movie.DeleteMovie;
import operation.movie.InsertMovie;
import operation.movie.SelectAllMovies;
import operation.movie.SelectMovies;
import operation.movie.UpdateMovie;
import operation.review.DeleteReview;
import operation.review.InsertReview;
import operation.review.SelectAllReviews;
import operation.review.SelectReviews;
import operation.review.UpdateReview;
import operation.user.DeleteUser;
import operation.user.InsertUser;
import operation.user.SelectAllUsers;
import operation.user.UpdateUser;
import operation.userMovieCollection.DeleteCollection;
import operation.userMovieCollection.InsertCollection;
import operation.userMovieCollection.SelectAllCollections;
import operation.userMovieCollection.SelectCollections;
import operation.userStatistics.SelectUserStatistics;
import server.Server;
import thread.ClientRequestHandler;
import view.coordinator.ServerFormCoordinator;

/**
 *
 * @author Mihailo
 */
public class Controller {
    private List<ClientRequestHandler> clients;
    private Server server;
    
    private static Controller controller;

    private Controller() {
        
        clients = new ArrayList<>();
    }
    
    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        
        return controller;
    }
    
    public User login(String username, String password) throws Exception{
        AbstractGenericOperation operation = new SelectAllUsers();
        operation.execute(new User());
        
        List<User> users = ((SelectAllUsers)operation).getUsers();
        
        for (User user : users) {
            if(user.getUsername().equals(username)&& user.getPassword().equals(password)){
                user.setStatus("online");
                return user;
            }
        }
        
        return null;
    }
    
    public List<User> selectUser(User user) throws Exception{
        AbstractGenericOperation operation = new SelectAllUsers();
        operation.execute(new User());
        
        List<User> users = ((SelectAllUsers)operation).getUsers();
        return users;
    }
    
    public void updateUser(User user) throws Exception{
        AbstractGenericOperation operation = new UpdateUser();
        operation.execute(user);
    }
    
    public void insertUser(User user) throws Exception{
        AbstractGenericOperation operation = new InsertUser();
        operation.execute(user);
    }
    
    public void deleteUser(User user) throws Exception {
        AbstractGenericOperation operation = new DeleteUser();
        operation.execute(user);
    }
    
    public List<Director> selectAllDirectors() throws Exception{
        AbstractGenericOperation operation = new SelectAllDirectors();
        operation.execute(new Director());
        List<Director> directors = ((SelectAllDirectors)operation).getDirectors();
        return directors;
    }
    
    public List<Movie> selectAllMovies() throws Exception {
        AbstractGenericOperation operation = new SelectAllMovies();
        operation.execute(new Movie());
        List<Movie> movies = ((SelectAllMovies)operation).getMovies();
        return movies;
    }
    
    public List<Actor> selectAllActors() throws Exception{
        AbstractGenericOperation operation = new SelectAllActors();
        operation.execute(new Actor());
        List<Actor> actors = ((SelectAllActors)operation).getActors();
        return actors;
    }
    
    public List<Genre> selectAllGenres() throws Exception{
        AbstractGenericOperation operation = new SelectAllGenres();
        operation.execute(new Genre());
        List<Genre> genres = ((SelectAllGenres)operation).getGenres();
        return genres;
    }
    
    public List<ProductionCompany> selectAllProductionCompanies() throws Exception{
        AbstractGenericOperation operation = new SelectAllProductionCompanies();
        operation.execute(new ProductionCompany());
        List<ProductionCompany> productionCompanies = ((SelectAllProductionCompanies)operation).getProductionCompanies();
        return productionCompanies;
    }
    
    public void insertMovie(Movie movie) throws Exception {
        AbstractGenericOperation operation = new InsertMovie();
        operation.execute(movie);
    }
    
    public void deleteMovie(Movie movie) throws Exception {
        AbstractGenericOperation operation = new DeleteMovie();
        operation.execute(movie);
    }

    public void updateMovie(Movie movie) throws Exception {
        AbstractGenericOperation operation = new UpdateMovie();
        operation.execute(movie);
    }
    
    public List<Movie> selectMovies(Movie movie) throws Exception {
        AbstractGenericOperation operation = new SelectMovies();
        operation.execute(movie);
        List<Movie> movies = ((SelectMovies)operation).getMovies();
        return movies;
    }
    
    public void insertDirector(Director director) throws Exception {
        AbstractGenericOperation operation = new InsertDirector();
        operation.execute(director);
    }
    
    public void deleteDirector(Director director) throws Exception {
        AbstractGenericOperation operation = new DeleteDirector();
        operation.execute(director);
    }

    public void updateDirector(Director director) throws Exception {
        AbstractGenericOperation operation = new UpdateDirector();
        operation.execute(director);
    }
    
    public void insertActor(Actor actor) throws Exception {
        AbstractGenericOperation operation = new InsertActor();
        operation.execute(actor);
    }
    
    public void deleteActor(Actor actor) throws Exception {
        AbstractGenericOperation operation = new DeleteActor();
        operation.execute(actor);
    }

    public void updateActor(Actor actor) throws Exception {
        AbstractGenericOperation operation = new DeleteActor();
        operation.execute(actor);
    }
    
    public void insertCollection(UserMovieCollection collection) throws Exception{
        AbstractGenericOperation operation = new InsertCollection();
        operation.execute(collection);
    }
    
    public List<UserMovieCollection> selectAllCollections() throws Exception{
        AbstractGenericOperation operation = new SelectAllCollections();
        operation.execute(new UserMovieCollection());
        List<UserMovieCollection> collections = ((SelectAllCollections)operation).getCollections();
        
        return collections;
    }
    
    public List<UserMovieCollection> selectCollections(UserMovieCollection col) throws Exception{
        AbstractGenericOperation operation = new SelectCollections();
        operation.execute(col);
        List<UserMovieCollection> collections = ((SelectCollections)operation).getCollections();
        
        return collections;
    }
    
    public void deleteCollection(UserMovieCollection collection) throws Exception{
        AbstractGenericOperation operation = new DeleteCollection();
        operation.execute(collection);
    }
    
    public void insertReview(Review review) throws Exception{
        AbstractGenericOperation operation = new InsertReview();
        operation.execute(review);
    }

    public List<Review> selectAllReviews() throws Exception{
        AbstractGenericOperation operation = new SelectAllReviews();
        operation.execute(new Review());
        List<Review> reviews = ((SelectAllReviews)operation).getReviews();
        
        return reviews;
    }
    
    public void deleteReview(Review review) throws Exception{
        AbstractGenericOperation operation = new DeleteReview();
        operation.execute(review);
    }
    
    public void updateReview (Review review) throws Exception {
        AbstractGenericOperation operation = new UpdateReview();
        operation.execute(review);
    }
    
    public List<Review> selectReviews(Review review) throws Exception{
        AbstractGenericOperation operation = new SelectReviews();
        operation.execute(review);
        List<Review> reviews = ((SelectReviews)operation).getReviews();
        
        return reviews;
    }
    
    public List<UserStatistics> selectUserStatistics() throws Exception{
        AbstractGenericOperation operation = new SelectUserStatistics();
        operation.execute(new UserStatistics());
        List<UserStatistics> userStats = ((SelectUserStatistics)operation).getUserStats();
        
        return userStats;
    }

    public List<ClientRequestHandler> getClients() {
        return clients;
    }
    
    public void addClient(ClientRequestHandler client) {
        clients.add(client);
    }
    
    public void startServer() {
        server = new Server();
        server.start();
    }
    
    public void stopServer() {
        for (ClientRequestHandler crh : clients) {
            try {
                crh.getUser().setStatus("offline");
                updateUser(crh.getUser());
                crh.logoutAll();
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ServerFormCoordinator.getInstance().getMainContoller().setUpTableuserStatistics();
        server.stopServer();
    }

    public void removeClient(ClientRequestHandler client) {
        clients.remove(client);
    }
}
