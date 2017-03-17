package com.epam.library.command;

import com.epam.library.exception.ServiceException;
import com.epam.library.service.impl.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class ReportCommand implements ICommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(String params) {
        String reportString = "";
        EmployeeService employeeService = new EmployeeService();
        try {
            switch (params) {
                case AvailableOperations.MORE_THAN_1_PARAM:
                    reportString = employeeService.employeesWithMoreThanOneBook();
                    break;
                case AvailableOperations.LESS_OR_EQ_THAN_2_PARAM:
                    reportString = employeeService.employeesWithLessOrEqThanTwoBooks();
                    break;
                default:
                    reportString = AvailableOperations.INVALID_PARAMETER_LIST_MESSAGE;
            }
        } catch (ServiceException e) {
            LOG.error("Error while executing report command.", e);
        }
        return reportString;
    }