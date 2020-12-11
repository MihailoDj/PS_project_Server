/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;

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
    public List<T> select(String criteria) throws Exception;
    public List<T> selectAll() throws Exception;
}
