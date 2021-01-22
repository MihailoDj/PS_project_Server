/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import controller.Controller;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.ClientRequestHandler;

/**
 *
 * @author Mihailo
 */
public class Server extends Thread{
    private ServerSocket serverSocket;
    private boolean stop = false;
    
    public Server () {
        
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(4000);
            
            while(!stop) {
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();

                System.out.println("Connected");
                handleClient(socket);
                
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleClient(Socket socket) throws Exception {
        ClientRequestHandler client = new ClientRequestHandler(socket);
        client.start();
        
        Controller.getInstance().addClient(client);
    }
    
    public void stopServer() {
        stop = true;
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
