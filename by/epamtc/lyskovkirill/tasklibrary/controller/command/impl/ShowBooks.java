package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.LibraryLogger;
import by.epamtc.lyskovkirill.tasklibrary.service.LibraryService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

import java.util.List;

public class ShowBooks extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        if (userContext.getRole() == UserRole.GUEST)
            response = "You can't use library now";
        else {
            if (request.length() > 0)
                response = wrongSignature;
            else {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                LibraryService libraryService = serviceFactory.getLibraryService();

                try {
                    List<Book> bookList = libraryService.findBooks();
                    if (bookList == null)
                        response = "Invalid input parameters!";
                    else {
                        StringBuilder responseBuilder = new StringBuilder();

                        responseBuilder.append("Books:\n");
                        for (Book book : bookList) {
                            responseBuilder.append(book).append('\n');
                        }
                        response = responseBuilder.toString();
                    }
                } catch (ServiceException e) {
                    response = "Error during showing all books";
                    LibraryLogger.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}
