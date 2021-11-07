package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.Log;
import by.epamtc.lyskovkirill.tasklibrary.service.LibraryService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

import java.util.List;

public class ShowFavourites extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        if (userContext.getRole() != UserRole.USER)
            response = "You can't see you favourites now";
        else {
            if (request.length() > 0)
                response = wrongSignature;
            else {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                LibraryService libraryService = serviceFactory.getLibraryService();

                try {
                    List<Book> bookList = libraryService.showFavourites(userContext);
                    if (bookList == null)
                        response = "Invalid input parameters!";
                    else {
                        StringBuilder responseBuilder = new StringBuilder();

                        responseBuilder.append("Favourites:\n");
                        for (Book book : bookList) {
                            responseBuilder.append(book).append('\n');
                        }
                        response = responseBuilder.toString();
                    }
                } catch (ServiceException e) {
                    response = "Error during showing favourites";
                    Log.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}
