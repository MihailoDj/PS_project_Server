/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.actor;

import domain.Actor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mihailo
 */
public class SelectAllActors extends AbstractGenericOperation{
    List<Actor> actors = new ArrayList<>();
    
    @Override
    protected void preconditions(Object param) throws Exception {
        
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.selectAll((Actor)param);
        
        while (rs.next()) {
            Actor actor = new Actor();
            actor.setActorID(rs.getLong("actorID"));
            actor.setFirstName(rs.getString("firstname"));
            actor.setLastName(rs.getString("lastname"));
            actor.setBiography(rs.getString("biography"));

            actors.add(actor);
        }
    }

    public List<Actor> getActors() {
        return actors;
    }
    
    
}
