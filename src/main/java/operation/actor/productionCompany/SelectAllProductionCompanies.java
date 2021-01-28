/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.actor.productionCompany;

import domain.ProductionCompany;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mihailo
 */
public class SelectAllProductionCompanies extends AbstractGenericOperation{
    List<ProductionCompany> productionCompanies = new ArrayList<>();
    
    @Override
    protected void preconditions(Object param) throws Exception {
        
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.selectAll((ProductionCompany)param);
        
        while (rs.next()) {
            ProductionCompany productionCompany = new ProductionCompany();
            productionCompany.setProductionCompanyID(rs.getLong("pcID"));
            productionCompany.setName(rs.getString("name"));

            productionCompanies.add(productionCompany);
            }
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }
    
    
}
