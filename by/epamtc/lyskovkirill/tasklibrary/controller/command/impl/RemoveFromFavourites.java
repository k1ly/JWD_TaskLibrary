package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.LibraryLogger;
import by.epamtc.lyskovkirill.tasklibrary.service.ClientService;
import by.epamtc.lyskovkirill.tasklibrary.service.LibraryService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

public class RemoveFromFavourites extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        String title;
        String author;
        String[] requestParams;

        if (userContext.getRole() != UserRole.USER)
            response = "You can't remove books from your favourites";
        else {
            requestParams = request.split(paramSeparator);
            if (requestParams.length != 2)
                response = wrongSignature;
            else {
                title = requestParams[0];
                author = requestParams[1];

                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                LibraryService libraryService = serviceFactory.getLibraryService();
                ClientService clientService = serviceFactory.getClientService();

                try {
                    Book book = libraryService.findByTitleAndAuthor(title, author);
                    if (book == null)
                        response = "Invalid input parameters!";
                    else {
                        userContext = clientService.removeFromFavourites(userContext, book.getId());
                        response = "Book removed from your favourites!";
                    }
                } catch (ServiceException e) {
                    response = "Error during removing book from favourites";
                    LibraryLogger.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}