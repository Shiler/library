package com.epam.library.command;

import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookService;
import com.epam.library.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class ViewCommand implements ICommand {

    //TODO: Add options to view single book and employee

    private static final Logger LOG = LogManager.getLogger();
    @Override
    public String execute(String params) {
        String response = "";
        try {
            response = processParameters(params);
        } catch (ServiceException e) {
            LOG.error("Error while executing 'view' command", e);
        }
        return response;
    }

    private String processParameters(String params) throws ServiceException {
        String reportString = "";
        switch (params) {
            case AvailableOperations.BOOKS_PARAM:
                BookService bookService = new BookService();
                reportString = bookService.showAll();
                break;
            case AvailableOperations.EMPLOYEES_LONG_PARAM:
            case AvailableOperations.EMPLOYEES_SHORT_PARAM:
                EmployeeService employeeService = new EmployeeService();
                reportString = employeeService.showAll();
                break;
            default:
                reportString = AvailableOperations.INVALID_PARAMETER_LIST_MESSAGE;
        }
        return reportString;
    }
}