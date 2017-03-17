package com.epam.library.command;

import com.epam.library.exception.ServiceException;
import com.epam.library.service.impl.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class RenameBookCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger();

    private static final String SUCCESS_MESSAGE = "Book was renamed.";

    @Override
    public String execute(String params) {
        BookService bookService = new BookService();
        String response;

        List<String> paramData = Arrays.asList(params.split(" "));

        if (paramData.isEmpty()) {
            return AvailableOperations.INVALID_PARAMETER_LIST_MESSAGE;
        }

        try {
            bookService.renameBook(paramData.get(0), paramData.get(1));
            response = SUCCESS_MESSAGE;
        } catch (ServiceException e) {
            response = AvailableOperations.INVALID_COMMAND_MESSAGE;
            logger.error("Error while renaming book");
        }
        return response;
    }
}
