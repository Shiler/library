package com.epam.library.dao.query;

import com.epam.library.dao.AbstractJDBCDao;
import com.epam.library.dao.impl.MySQLBookDao;
import com.epam.library.dao.impl.MySQLEmployeeDao;
import com.epam.library.dao.query.exception.MissingPropertyException;
import com.epam.library.util.PropertyLoader;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Yauhen_Yushkevich on 29.03.17.
 */
public final class CRUDQueryComposer {

    private final static Map<Class, DaoName> DAO_NAMES = new HashMap<>();

    static {
        DAO_NAMES.put(MySQLBookDao.class, DaoName.BOOK);
        DAO_NAMES.put(MySQLEmployeeDao.class, DaoName.EMPLOYEE);
    }

    private final Properties properties;
    private final DaoName daoName;

    public CRUDQueryComposer(Class<? extends AbstractJDBCDao> caller) throws FileNotFoundException {
        properties = PropertyLoader.loadProperties("src/main/resources/data-tables.properties");
        daoName = DAO_NAMES.get(caller);
    }

    public String composeCreateQuery() throws MissingPropertyException {
        StringBuffer query = new StringBuffer();

        String tableName = properties.getProperty(daoName + ".table");
        String fieldsString = properties.getProperty(daoName + ".create-fields");

        if (tableName == null || fieldsString == null) {
            throw new MissingPropertyException("Unable to compose query: property does not exist!");
        }

        String[] fields = splitFields(fieldsString);

        query.append("INSERT INTO `")
                .append(tableName)
                .append("` (")
                .append(enumeratedFieldsWithQuotes(fields))
                .append(") VALUES ")
                .append(enumeratedQuestionFields(fields.length))
                .append(";");

        return query.toString();
    }

    public String composeReadQuery() throws MissingPropertyException {
        StringBuffer query = new StringBuffer();

        String tableName = properties.getProperty(daoName + ".table");
        String fieldsString = properties.getProperty(daoName + ".select-fields");

        if (tableName == null || fieldsString == null) {
            throw new MissingPropertyException("Unable to compose query: property does not exist!");
        }

        String[] fields = splitFields(fieldsString);

        query.append("SELECT ")
                .append(enumeratedFieldsWithQuotes(fields))
                .append(" FROM ")
                .append("`").append(tableName).append("`;");

        return query.toString();
    }

    public String composeUpdateQuery() throws MissingPropertyException {
        StringBuffer query = new StringBuffer();

        String tableName = properties.getProperty(daoName + ".table");
        String fieldsString = properties.getProperty(daoName + ".update-fields");
        String where = properties.getProperty(daoName + ".update-where");

        if (tableName == null || fieldsString == null || where == null) {
            throw new MissingPropertyException("Unable to compose query: property does not exist!");
        }

        String[] fields = splitFields(fieldsString);

        query.append("UPDATE ")
                .append("`").append(tableName).append("`")
                .append(" SET ")
                .append(enumeratedSetFields(fields))
                .append(" WHERE `")
                .append(where).append("` = ?;");

        return query.toString();
    }

    public String composeDeleteQuery() throws MissingPropertyException {
        StringBuffer query = new StringBuffer();

        String tableName = properties.getProperty(daoName + ".table");
        String where = properties.getProperty(daoName + ".delete-where");

        if (tableName == null || where == null) {
            throw new MissingPropertyException("Unable to compose query: property does not exist!");
        }

        query.append("DELETE FROM `")
                .append(tableName).append("`")
                .append(" WHERE ")
                .append("`").append(where).append("` = ?;");

        return query.toString();
    }

    /**
     * @param fields
     * @return String like `field1` = ?, `field2` = ?, `field3` = ? for fields.length = 3
     */
    private String enumeratedSetFields(String[] fields) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            result.append("`").append(fields[i]).append("` = ?");
            if (i + 1 != fields.length) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    /**
     * @param fields
     * @return String like `field1`, `field2`, `field3` for fields.length = 3
     */
    private String enumeratedFieldsWithQuotes(String[] fields) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            result.append("`").append(fields[i]).append("`");
            if (i + 1 != fields.length) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    /**
     * @param amount
     * @return String like (?, ?, ?, ?) for fields.length = 4
     */
    private String enumeratedQuestionFields(int amount) {
        StringBuffer result = new StringBuffer();
        result.append("(");
        for (int i = 0; i < amount; i++) {
            if (i + 1 != amount) {
                result.append("?, ");
            } else {
                result.append("?)");
            }
        }
        return result.toString();
    }

    private String[] splitFields(String fieldsString) {
        return Arrays.stream(fieldsString.split(",")).map(String::trim).toArray(String[]::new);
    }

}
