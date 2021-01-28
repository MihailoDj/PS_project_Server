/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.review;

import domain.Review;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mihailo
 */
public class DeleteReview extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.delete((Review)param);
        //update average movie score
    }
    
}
