/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.user;

import domain.User;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mihailo
 */
public class SelectUser extends AbstractGenericOperation{
    List<User> users = new ArrayList<>();
    
    @Override
    protected void preconditions(Object param) throws Exception {
        
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.select((User)param);
        
        while (rs.next()) {
            User user = new User(); 
            user.setUserID(rs.getLong("userID"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setStatus(rs.getString("status"));

            users.add(user);
        }
    }

    public List<User> getUsers() {
        return users;
    }
    
    
}
