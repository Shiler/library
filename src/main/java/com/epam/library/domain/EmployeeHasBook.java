package com.epam.library.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Yauhen_Yushkevich on 21.03.17.
 */
public class EmployeeHasBook implements Identified<Integer>, Serializable {

    private int id;
    private Employee employee;
    private Book employeeBook;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Book getEmployeeBook() {
        return employeeBook;
    }

    public void setEmployeeBook(Book employeeBook) {
        this.employeeBook = employeeBook;
    }

}
