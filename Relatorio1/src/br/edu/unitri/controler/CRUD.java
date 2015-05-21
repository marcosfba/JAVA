/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author marcos.fernando
 *
 */

public interface CRUD<T, I> {

	T save(T t) throws SQLException;

	boolean delete(T t) throws SQLException;

	boolean update(T t, I i) throws SQLException;

	T getById(I i) throws SQLException;

	List<T> findAll() throws SQLException;

	List<T> findAll(String qry, String parametros) throws SQLException;

}
