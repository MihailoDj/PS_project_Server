/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import comm.Receiver;
import comm.Request;
import comm.Response;
import comm.Sender;
import controller.Controller;
import domain.Actor;
import domain.Director;
import domain.Movie;
import domain.Review;
import domain.User;
import domain.UserMovieCollection;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Mihailo
 */
public class Server {
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(4000);
            System.out.println("Waiting for connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Connected");
            
            handleClient(socket);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleClient(Socket socket) throws Exception {
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        
        while(true) {
            Request request = (Request) receiver.receive();
            Response response = new Response();
            
            try{
                switch(request.getOperation()) {
                    case LOGIN:
                        User userLogin = (User)request.getArgument();
                        response.setResult(Controller.getInstance().login(userLogin.getUsername(), userLogin.getPassword()));
                        break;
                    case INSERT_USER:
                        User userInsert = (User)request.getArgument();
                        Controller.getInstance().insertUser(userInsert);
                        break;
                    case UPDATE_USER:
                        User userUpdate = (User)request.getArgument();
                        Controller.getInstance().updateUser(userUpdate);
                        break;
                    case DELETE_USER:
                        User userDelete = (User)request.getArgument();
                        Controller.getInstance().deleteUser(userDelete);
                        break;
                        
                        // TODO select_movies
                        
                    case SELECT_ALL_MOVIES:
                        response.setResult(Controller.getInstance().selectAllMovies());
                        break;
                    case INSERT_MOVIE:
                        Movie movieInsert = (Movie)request.getArgument();
                        Controller.getInstance().insertMovie(movieInsert);
                        response.setResult(movieInsert);
                        break;
                    case UPDATE_MOVIE:
                        Movie movieUpdate = (Movie)request.getArgument();
                        Controller.getInstance().updateMovie(movieUpdate);
                        break;
                    case DELETE_MOVIE:
                        Movie movieDelete = (Movie)request.getArgument();
                        Controller.getInstance().deleteMovie(movieDelete);
                        break;
                    case SELECT_ALL_DIRECTORS:
                        response.setResult(Controller.getInstance().selectAllDirectors());
                        break;
                    case INSERT_DIRECTOR:
                        Director directorInsert = (Director)request.getArgument();
                        Controller.getInstance().insertDirector(directorInsert);
                        break;
                    case UPDATE_DIRECTOR:
                        Director directorUpdate = (Director)request.getArgument();
                        Controller.getInstance().updateDirector(directorUpdate);
                        break;
                    case DELETE_DIRECTOR:
                        Director directorDelete = (Director)request.getArgument();
                        Controller.getInstance().deleteDirector(directorDelete);
                        break;
                    case SELECT_ALL_ACTORS:
                        response.setResult(Controller.getInstance().selectAllActors());
                        break;
                    case INSERT_ACTOR:
                        Actor actorInsert = (Actor)request.getArgument();
                        Controller.getInstance().insertActor(actorInsert);
                        break;
                    case UPDATE_ACTOR:
                        Actor actorUpdate = (Actor)request.getArgument();
                        Controller.getInstance().updateActor(actorUpdate);
                        break;
                    case DELETE_ACTOR:
                        Actor actorDelete = (Actor)request.getArgument();
                        Controller.getInstance().deleteActor(actorDelete);
                        break;
                    case SELECT_ALL_COLLECTIONS:
                        response.setResult(Controller.getInstance().selectAllCollections());
                        break;
                    case INSERT_COLLECTION:
                        UserMovieCollection collectionInsert = (UserMovieCollection)request.getArgument();
                        Controller.getInstance().insertCollection(collectionInsert);
                        break;
                    case DELETE_COLLECTION:
                        UserMovieCollection collectionDelete = (UserMovieCollection)request.getArgument();
                        Controller.getInstance().deleteCollection(collectionDelete);
                        break;
                    case SELECT_ALL_REVIEWS:
                        response.setResult(Controller.getInstance().selectAllReviews());
                        break;
                    case INSERT_REVIEW:
                        Review reviewInsert = (Review)request.getArgument();
                        Controller.getInstance().insertReview(reviewInsert);
                        break;
                    case UPDATE_REVIEW:
                        Review reviewUpdate = (Review)request.getArgument();
                        Controller.getInstance().updateReview(reviewUpdate);
                        break;
                    case DELETE_REVIEW:
                        Review reviewDelete = (Review)request.getArgument();
                        Controller.getInstance().deleteReview(reviewDelete);
                        break;
                    case SELECT_ALL_PRODUCTION_COMPANIES:
                        response.setResult(Controller.getInstance().selectAllProductionCompanies());
                        break;
                    case SELECT_ALL_GENRES:
                        response.setResult(Controller.getInstance().selectAllGenres());
                        break;
                }
            } catch(Exception ex) {
                ex.printStackTrace();
                response.setException(ex);
            }
            sender.send(response);
        }
    }
    
}
