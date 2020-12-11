/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import domain.Director;

/**
 *
 * @author Mihailo
 */
public class DirectorRepository {
    private final List<Director> directors;
    
    public DirectorRepository() {
        directors = new ArrayList<Director>(){
              
        };
    }
    
    public List<Director> getAll() {
        return directors;
    }
}
