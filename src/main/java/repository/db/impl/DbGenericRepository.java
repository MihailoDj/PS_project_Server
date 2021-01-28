/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.GenericEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author Mihailo
 */
public class DbGenericRepository implements DbRepository<GenericEntity>{

    @Override
    public void insert(GenericEntity entity) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ")
                    .append(entity.getTableName())
                    .append(" (").append(entity.getColumnNamesForInsert()).append(")")
                    .append(" VALUES (");
            
            for (int i = 0; i < entity.getColumnCount(); i++) {
                sb.append("?,");
            }
            
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
            String query = sb.toString();
            System.out.println(query);
            
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            entity.getInsertValues(statement);
            
            statement.executeUpdate();
            ResultSet rsKey = statement.getGeneratedKeys();
            
            if (rsKey.next()) {
                Long id = rsKey.getLong(1);
                entity.setId(id);
            }
            
            statement.close();
            rsKey.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void delete(GenericEntity entity) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(entity.getTableName())
                    .append(" WHERE ").append(entity.getConditionForDelete());
            
        PreparedStatement statement = connection.prepareStatement(sb.toString());
        statement.executeUpdate();
        
        statement.close();
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(GenericEntity entity) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(entity.getTableName())
                    .append(" SET ").append(entity.getColumnNamesForUpdate())
                    .append(" WHERE ").append(entity.getConditionForUpdate());
            
            
            String query = sb.toString();
            System.out.println(query);
            
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            entity.getInsertValues(statement);
            
            statement.executeUpdate();
            ResultSet rsKey = statement.getGeneratedKeys();
            
            if (rsKey.next()) {
                Long id = rsKey.getLong(1);
                entity.setId(id);
            }
            
            statement.close();
            rsKey.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public ResultSet select(GenericEntity entity) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ").append(entity.getColumnNamesForSelect())
                    .append(" FROM ").append(entity.getTableForSelect());
            
            if (!entity.getConditionForSelectSpecific().equals("")) {
                sb.append(" WHERE ").append(entity.getConditionForSelectSpecific());
            }
            
            String query = sb.toString();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(DbGenericRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error loading movies!");
        }
    }

    @Override
    public ResultSet selectAll(GenericEntity entity) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ").append(entity.getColumnNamesForSelect())
                    .append(" FROM ").append(entity.getTableForSelect());
            
            if (!entity.getConditionForSelect().equals("")) {
                sb.append(" WHERE ").append(entity.getConditionForSelect());
            }
            
            String query = sb.toString();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(DbGenericRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error loading movies!");
        }
    }
    
}
