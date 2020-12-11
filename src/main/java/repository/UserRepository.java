/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domain.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mihailo
 */
public class UserRepository {
     private final List<User> users;
    
    public UserRepository() {
        users = new ArrayList<User>() {
            {
                add(new User(1, "admin", "admin", true));
                add(new User(2, "korisnik", "korisnik", false));
            }
        };
    }
    
    public List<User> getAll() {
        
        return users;
    }
}
