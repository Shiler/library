package com.epam.library.dao;

import com.epam.library.dao.exception.PersistException;
import com.epam.library.dao.query.CRUDQueryComposer;
import com.epam.library.dao.query.exception.MissingPropertyException;
import com.epam.library.database.DatabaseUtil;
import com.epam.library.database.StandaloneConnectionPool;
import com.epam.library.domain.Identified;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract class provides basic implementation of the CRUD operations
 * using JDBC.
 *
 * @param <T>  persistence object type
 * @param <PK> primary key type
 */
public abstract class AbstractJDBCDao<T extends Identified<PK>,
        PK extends Integer> implements GenericDao<T, PK> {

    private final static Logger logger = LogManager.getLogger(AbstractJDBCDao.class);

    private DaoFactory parentFactory;
    private Set<ManyToOne> relations = new HashSet<>();

    private CRUDQueryComposer crudQueryComposer;

    public AbstractJDBCDao(DaoFactory parentFactory) {
        this.parentFactory = parentFactory;
        try {
            crudQueryComposer = new CRUDQueryComposer(this.getClass());
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
    }

    /**
     * Returns a sql query for getting all records.
     * <p/>
     * SELECT * FROM [Table]
     */

    private String getSelectQuery() {
        String query = null;
        try {
            query = crudQueryComposer.composeReadQuery();
        } catch (MissingPropertyException e) {
            logger.error(e);
        }
        return query;
    }

    /**
     * Returns a sql query for a new record inserting to the database.
     * <p/>
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     */
    private String getCreateQuery() {
        String query = null;
        try {
            query = crudQueryComposer.composeCreateQuery();
        } catch (MissingPropertyException e) {
            logger.error(e);
        }
        return query;
    }

    /**
     * Returns a sql query for updating the records.
     * <p/>
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     */
    private String getUpdateQuery() {
        String query = null;
        try {
            query = crudQueryComposer.composeUpdateQuery();
        } catch (MissingPropertyException e) {
            logger.error(e);
        }
        return query;
    }

    /**
     * Returns a sql query for record deleting from the database.
     * <p/>
     * DELETE FROM [Table] WHERE id= ?;
     */
    private String getDeleteQuery() {
        String query = null;
        try {
            query = crudQueryComposer.composeDeleteQuery();
        } catch (MissingPropertyException e) {
            logger.error(e);
        }
        return query;
    }

    /**
     * Decomposes {@link ResultSet} and returns <code>List</code> of objects
     * relevant to the <code>ResultSet</code>
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws PersistException;

    /**
     * Sets up arguments of the <code>insert</code> query
     * in accordance with the values of the object fields
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object)
            throws PersistException;

    /**
     * Sets up arguments of the <code>update</code> query
     * in accordance with the values of the object fields
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;

    @Override
    public T getByPK(Integer key) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        PreparedStatement statement = null;
        try (Connection connection = StandaloneConnectionPool.takeConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            DatabaseUtil.closeStatement(statement);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }

    @Override
    public List<T> getAll() throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        PreparedStatement statement = null;
        try (Connection connection = StandaloneConnectionPool.takeConnection()) {
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            DatabaseUtil.closeStatement(statement);
        }
        return list;
    }

    @Override
    public T persist(T object) throws PersistException {
        saveDependences(object);
        T persistInstance;

        String sql = getCreateQuery();
        PreparedStatement statement = null;
        try (Connection connection = StandaloneConnectionPool.takeConnection()) {
            statement = connection.prepareStatement(sql);
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On persist modify more then 1 record: " + count);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            DatabaseUtil.closeStatement(statement);
        }

        sql = getSelectQuery() + " WHERE id = last_insert_id();";
        try (Connection connection = StandaloneConnectionPool.takeConnection()) {
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new PersistException("Exception on findByPK new persist data.");
            }
            persistInstance = list.iterator().next();
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            DatabaseUtil.closeStatement(statement);
        }
        return persistInstance;
    }

    @Override
    public void update(T object) throws PersistException {

        String sql = getUpdateQuery();
        PreparedStatement statement = null;
        try (Connection connection = StandaloneConnectionPool.takeConnection()) {
            statement = connection.prepareStatement(sql);
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            DatabaseUtil.closeStatement(statement);
        }
    }

    @Override
    public void delete(T object) throws PersistException {
        String sql = getDeleteQuery();
        PreparedStatement statement = null;
        try (Connection connection = StandaloneConnectionPool.takeConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, object.getId());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            DatabaseUtil.closeStatement(statement);
        }
    }

    /**
     * Returns dependence of the DAO
     *
     * @param dtoClass model class
     * @param pk       primary key of the record
     * @return depending model object
     * @throws PersistException
     */
    protected Identified getDependence(Class<? extends Identified> dtoClass, Serializable pk) throws PersistException {
        return parentFactory.getDao(dtoClass).getByPK(pk);
    }

    protected boolean addRelation(Class<? extends Identified> ownerClass, String field) {
        try {
            return relations.add(new ManyToOne(ownerClass, parentFactory, field));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveDependences(Identified owner) throws PersistException {
        for (ManyToOne m : relations) {
            try {
                if (m.getDependence(owner) == null) {
                    continue;
                }
                if (m.getDependence(owner).getId() == null) {
                    Identified depend = m.persistDependence(owner);
                    m.setDependence(owner, depend);
                } else {
                    m.updateDependence(owner);
                }
            } catch (Exception e) {
                throw new PersistException("Exception on save dependence in relation " + m + ".", e);
            }
        }
    }

}