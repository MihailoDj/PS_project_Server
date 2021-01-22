/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import comm.Request;
import comm.Response;
import controller.Controller;
import domain.Movie;
import domain.Review;
import domain.User;
import domain.UserMovieCollection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mihailo
 */
public class ClientRequestHandler extends Thread{
    private Socket socket;
    private User user;

    public ClientRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            
            while(true) {
                Request request = receiveUserRequest();
                Response response = new Response();
                response.setOperation(request.getOperation());

                try{
                    switch(request.getOperation()) {
                        case LOGIN:
                            User userLogin = (User)request.getArgument();
                            
                            User loggedInUser = Controller.getInstance().login(userLogin.getUsername(), userLogin.getPassword());
                            
                            if (loggedInUser != null) {
                                response.setResult(loggedInUser);
                                this.user = loggedInUser;
                            } else {
                                response.setResult(null);
                            }
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
                        case SELECT_USER: 
                            User userSelect = (User)request.getArgument();
                            response.setResult(Controller.getInstance().selectUser(userSelect));
                            break;
                        case SELECT_MOVIES:
                            Movie movieSelect = (Movie)request.getArgument();
                            response.setResult(Controller.getInstance().selectMovies(movieSelect));
                            break;
                        case SELECT_ALL_MOVIES:
                            response.setResult(Controller.getInstance().selectAllMovies());
                            break;
                        case SELECT_ALL_COLLECTIONS:
                            response.setResult(Controller.getInstance().selectAllCollections());
                            break;
                        case SELECT_COLLECTIONS:
                            UserMovieCollection collectionSelect = (UserMovieCollection)request.getArgument();
                            response.setResult(Controller.getInstance().selectCollections(collectionSelect));
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
                        case SELECT_REVIEWS:
                            Review reviewSelect = (Review)request.getArgument();
                            response.setResult(Controller.getInstance().selectReviews(reviewSelect));
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
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                    response.setException(ex);
                }
                sendServerResponse(socket, response);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    private Request receiveUserRequest() {
        Request request = new Request();
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            request = (Request) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ClientRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return request;
    }

    private void sendServerResponse(Socket s, Response response) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(response);
        } catch (IOException ex) {
            Logger.getLogger(ClientRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
