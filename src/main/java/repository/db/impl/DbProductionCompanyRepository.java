/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.ProductionCompany;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author Mihailo
 */
public class DbProductionCompanyRepository implements DbRepository<ProductionCompany>{

    @Override
    public void insert(ProductionCompany obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(ProductionCompany obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ProductionCompany obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProductionCompany> select(String obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProductionCompany> selectAll() throws Exception {
        try{
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<ProductionCompany> productionCompanies = new ArrayList<ProductionCompany>();
            
            String sql = "SELECT * FROM productioncompany ORDER BY name ASC";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                ProductionCompany productionCompany = new ProductionCompany() {
                    {
                        setProductionCompanyID(rs.getInt("pcID"));
                        setName(rs.getString("name"));
                    }
                };
                productionCompanies.add(productionCompany);
            }
            
            return productionCompanies;
        } catch (SQLException ex) {
            Logger.getLogger(DbProductionCompanyRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error loading production companies!");
        }
    }
    
}
