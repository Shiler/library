package com.epam.library.service;

import com.epam.library.domain.Employee;
import com.epam.library.exception.ServiceException;

import java.util.List;

/**
 * Created by Yauhen_Yushkevich on 17.03.17.
 */
public interface IEmployeeService {

    List<Employee> list() throws ServiceException;

    List<Employee> employeesWithMoreThanOneBook() throws ServiceException;

    List<Employee> employeesWithLessOrEqThanTwoBooks() throws ServiceException;

}
