/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.sql.ResultSet;

/**
 *
 * @author Mihailo
 * @param <T>
 */
public interface Repository<T> {
    public void insert(T obj) throws Exception;
    public void delete(T obj) throws Exception;
    public void deleteAll() throws Exception;
    public void update(T obj) throws Exception;
    public ResultSet select(T obj) throws Exception;
    public ResultSet selectAll(T obj) throws Exception;
}
