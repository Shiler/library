package com.epam.library.command;

import com.epam.library.exception.ServiceException;
import com.epam.library.service.BookService;
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
        String response = "";

        List<String> paramData = Arrays.asList(params.split(" "));

        if (paramData.isEmpty()) {
            return AvailableOperations.INVALID_PARAMETER_LIST_MESSAGE;
        }

        try {
            switch (paramData.get(MASK_OR_TITLE_INDEX)) {
                case AvailableOperations.MASK_PARAM:
                    bookService.renameBook(paramData.get(OLD_MASK_OR_TITLE_INDEX), paramData.get(NEW_MASK_OR_TITLE_INDEX), true);
                    response = SUCCESS_MESSAGE;
                    break;
                case AvailableOperations.TITLE_PARAM:
                    bookService.renameBook(paramData.get(OLD_MASK_OR_TITLE_INDEX), paramData.get(NEW_MASK_OR_TITLE_INDEX), false);
                    response = SUCCESS_MESSAGE;
                    break;
                default:
                    response = AvailableOperations.INVALID_PARAMETER_LIST_MESSAGE;
            }
        } catch (ServiceException e) {
            logger.error("Error while executing book rename operation.");
        }

        return response;
    }
}