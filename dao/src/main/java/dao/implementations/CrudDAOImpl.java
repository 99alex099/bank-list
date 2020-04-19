package dao.implementations;

import dao.interfaces.CrudDAO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public abstract class CrudDAOImpl<T> implements CrudDAO<T> {

    private final String CONDITION_ALL_ENTITIES = "1";

    private final Connection CONNECTION;

    public CrudDAOImpl() {
        CONNECTION = ConnecterSQL.getConnecter().getConnection();
    }

    public T add(T entity) {
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(
                    formInsertQuery(entity),
                    ConnecterSQL.getConnecter().getStatement().RETURN_GENERATED_KEYS);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating plane failed, no rows affected.");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                setEntityId(entity, generatedKeys.getInt(1)); // !!!2
            } else {
                throw new SQLException("Creating plane failed, no ID obtained.");
            }
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T loadById(Integer id) {
        try {
            ResultSet resultSet = ConnecterSQL.getConnecter().getStatement().executeQuery(
                    formSelectQuery("`" + getIdName() + "` = " + id.toString()));

            if (resultSet.next()) {
                return createEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T save(T entity) {
        try {
            PreparedStatement preparedStatement = ConnecterSQL.getConnecter().getConnection().prepareStatement(
                    formUpdateQuery(entity));

            if (preparedStatement.executeUpdate() > 0) {
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T delete(T entity) {
        try {
            PreparedStatement preparedStatement = ConnecterSQL.getConnecter().getConnection().prepareStatement(
                    formDeleteQuery());

            preparedStatement.setString(1, getEntityId(entity).toString());

            if (preparedStatement.executeUpdate() > 0) {
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> findAll() {
        List<T> list = new LinkedList<>();

        try {
            ResultSet resultSet = ConnecterSQL.getConnecter().getStatement().executeQuery(
                    formSelectQuery(CONDITION_ALL_ENTITIES));
            if (resultSet != null) {
                while (resultSet.next()) {
                    list.add(createEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    abstract protected String getTableName();

    abstract protected String formSelectQuery(String condition);

    abstract protected String formInsertQuery(T entity);

    abstract protected String formUpdateQuery(T entity);

    abstract protected T createEntity(ResultSet resultSet) throws SQLException;

    abstract protected String getIdName();

    abstract protected void setEntityId(T info, int newIndex);

    private String formDeleteQuery() {
        return "DELETE FROM `" + getTableName() + "` WHERE `" + getIdName() + "` = ?";
    }

    protected abstract Integer getEntityId(T entity);
}

