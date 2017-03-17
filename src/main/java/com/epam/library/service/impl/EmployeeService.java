package com.epam.library.service.impl;

import com.epam.library.dao.DaoFactory;
import com.epam.library.dao.exception.PersistException;
import com.epam.library.dao.impl.MySqlDaoFactory;
import com.epam.library.dao.impl.MySqlEmployeeDao;
import com.epam.library.domain.Employee;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.IEmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by Yauhen_Yushkevich on 16.03.17.
 */
public class EmployeeService implements IEmployeeService {

    private final static Logger logger = LogManager.getLogger(EmployeeService.class);
    private DaoFactory daoFactory;
    private MySqlEmployeeDao employeeDao;

    public EmployeeService() {
        daoFactory = new MySqlDaoFactory();
        try {
            employeeDao = (MySqlEmployeeDao) daoFactory.getDao(Employee.class);
        } catch (PersistException e) {
            logger.error("Unable to get EmployeeDao");
        }
    }

    @Override
    public List<Employee> list() throws ServiceException {
        try {
            return employeeDao.getAll();
        } catch (PersistException e) {
            throw new ServiceException();
        }
    }

    public static String listToString(List<Employee> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Employee employee : list) {
            stringBuffer.append(employee.getId());
            stringBuffer.append("\t");
            stringBuffer.append(employee.getName());
            stringBuffer.append("\t");
            stringBuffer.append(employee.getEmail());
            stringBuffer.append("\t");
            stringBuffer.append(employee.getDateOfBirth());
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    @Override
    public List<Employee> employeesWithMoreThanOneBook() throws ServiceException {
        return null;
    }

    @Override
    public List<Employee> employeesWithLessOrEqThanTwoBooks() throws ServiceException {
        return null;
    }
}
