package dao.interfaces;

import java.util.List;

public interface CrudDAO<T> {
    T loadById(Integer id);
    T save(T entity);
    T delete(T entity);
    T add(T entity);
    List<T> findAll();
}
