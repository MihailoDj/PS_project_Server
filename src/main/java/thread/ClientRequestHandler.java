/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import comm.Operation;
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
import java.security.SecureRandom;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import view.coordinator.ServerFormCoordinator;

/**
 *
 * @author Mihailo
 */
public class ClientRequestHandler extends Thread{
    private Socket socket;
    private boolean stop = false;
    private User user;

    public ClientRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        while(!stop) {
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
                                Controller.getInstance().updateUser(user);
                                ServerFormCoordinator.getInstance().getMainContoller().setUpTableuserStatistics();
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
                            ServerFormCoordinator.getInstance().getMainContoller().setUpTableuserStatistics();
                            break;
                        case DELETE_USER:
                            User userDelete = (User)request.getArgument();
                            Controller.getInstance().deleteUser(userDelete);
                            ServerFormCoordinator.getInstance().getMainContoller().setUpTableuserStatistics();
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
                        case VERIFY_CODE:
                            User userToVerify = (User)request.getArgument();
                            String email = userToVerify.getEmail();
                            
                            String verificationCode = generateCode(6);
                            
                            String from = "kinoteka.ps";
                            String pass = "Kinoteka1234";
                            String[] to = new String[]{email};
                            String subject = "Account verification";
                            String body = "Your verification code is " + verificationCode + ".";
        
                            sendEmail(from, pass, to, subject, body);
                            
                            response.setResult(verificationCode);
                            break;
                        case VERIFY_DEACTIVATION:
                            User userToDeactivate = (User)request.getArgument();
                            String email1 = userToDeactivate.getEmail();
                            
                            String deactivationCode = generateCode(6);
                            
                            String from1 = "kinoteka.ps";
                            String pass1 = "Kinoteka1234";
                            String[] to1 = new String[]{email1};
                            String subject1 = "Account deactivation";
                            String body1 = "Your account deactivation code is " + deactivationCode + ".";
        
                            sendEmail(from1, pass1, to1, subject1, body1);
                            
                            response.setResult(deactivationCode);
                            break;
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                    response.setException(ex);
                }
                sendServerResponse(socket, response);
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
    
    public Response logoutAll() {
        stop = true;
        
        Response response = new Response();
        response.setOperation(Operation.LOGOUT_ALL);
        sendServerResponse(socket, response);
        return response;
    }
    
    private static void sendEmail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    String generateCode(int len){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

       StringBuilder sb = new StringBuilder(len);
       for(int i = 0; i < len; i++)
          sb.append(AB.charAt(rnd.nextInt(AB.length())));
       return sb.toString();
    }
}
