package com.epam.library.dao.query;

import com.epam.library.dao.query.exception.MissingPropertyException;
import com.epam.library.util.PropertyLoader;

import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * Created by Yauhen_Yushkevich on 29.03.17.
 */
public final class CRUDQueryComposer {

    private final Properties properties;

    public CRUDQueryComposer() throws FileNotFoundException {
        properties = PropertyLoader.loadProperties("src/main/resources/data-tables.properties");
    }

    public String composeCreateQuery(DaoName daoName) throws MissingPropertyException {
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

    public String composeReadQuery(DaoName daoName) throws MissingPropertyException {
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

    public String composeUpdateQuery(DaoName daoName) throws MissingPropertyException {

    }

    /**
     * @param fields
     * @return String like `field1`, `field2`, `field3` for fields.length = 3
     */
    private String enumeratedFieldsWithQuotes(String[] fields) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            if (i + 1 != fields.length) {
                result.append("`").append(fields[i]).append("`, ");
            } else {
                result.append(fields[i]);
            }

        }
        return result.toString();
    }

    /**
     * @param amount
     * @return String like (?, ?, ?, ?) for amount = 4
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
        return fieldsString.split(",");
    }

}
