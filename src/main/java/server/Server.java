/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import controller.Controller;
import domain.Movie;
import domain.User;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Mihailo
 */
public class Server {
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);
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
                        User user = (User)request.getArgument();
                        response.setResult(Controller.getInstance().login(user.getUsername(), user.getPassword()));
                        break;
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
                        Controller.getInstance().insertMovie(movieDelete);
                        break;
                    case SELECT_ALL_DIRECTORS:
                        response.setResult(Controller.getInstance().selectAllDirectors());
                        break;
                    case SELECT_ALL_ACTORS:
                        response.setResult(Controller.getInstance().selectAllActors());
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
