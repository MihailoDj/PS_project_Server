/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.director;

import domain.Director;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mihailo
 */
public class SelectAllDirectors extends AbstractGenericOperation{
    private List<Director> directors = new ArrayList<>();
    
    @Override
    protected void preconditions(Object param) throws Exception {
        
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.selectAll((Director)param);
        
        while (rs.next()) {
            Director director = new Director();
            director.setDirectorID(rs.getLong("directorID"));
            director.setFirstName(rs.getString("firstname"));
            director.setLastName(rs.getString("lastname"));
            director.setDateOfBirth(rs.getObject("dateofbirth", LocalDate.class));

            directors.add(director);
        }
    }

    public List<Director> getDirectors() {
        return directors;
    }
    
    
}
