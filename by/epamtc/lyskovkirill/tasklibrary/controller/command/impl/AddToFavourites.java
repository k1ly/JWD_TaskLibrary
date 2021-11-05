package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.Log;
import by.epamtc.lyskovkirill.tasklibrary.service.ClientService;
import by.epamtc.lyskovkirill.tasklibrary.service.LibraryService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

public class AddToFavourites extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        String title;
        String author;
        String[] requestParams;

        if (userContext.getRole() != UserRole.USER)
            response = "You can't add books to your favourites";
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
                    userContext = clientService.addToFavourites(userContext, book.getId());
                    response = "Book added to your favourites!";
                } catch (ServiceException e) {
                    response = "Error during adding book to favourites";
                    Log.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}
