package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.Log;
import by.epamtc.lyskovkirill.tasklibrary.service.LibraryService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

import java.util.List;

public class FindBooksBy extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        String attribute;
        String searchingFilter;
        String[] requestParams;

        if (userContext.getRole() == UserRole.GUEST)
            response = "You can't use library now";
        else {
            requestParams = request.split(paramSeparator);
            if (requestParams.length != 2)
                response = wrongSignature;
            else {
                attribute = requestParams[0];
                searchingFilter = requestParams[1];

                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                LibraryService libraryService = serviceFactory.getLibraryService();

                try {
                    List<Book> bookList;
                    StringBuilder responseBuilder = new StringBuilder();
                    bookList = libraryService.findByAttribute(attribute.toUpperCase(), searchingFilter);
                    responseBuilder.append("Books where ").append(attribute.toLowerCase())
                            .append(" is ").append(searchingFilter).append(":\n");
                    for (Book book : bookList) {
                        responseBuilder.append(book.toString()).append(":\n");
                    }
                    response = responseBuilder.toString();
                } catch (ServiceException e) {
                    response = "Error during book searching";
                    Log.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}