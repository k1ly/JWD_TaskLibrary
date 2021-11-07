package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.BookGenre;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.Command;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.Log;
import by.epamtc.lyskovkirill.tasklibrary.service.LibraryService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

public class AddBook extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        String title;
        String author;
        String genre;
        String[] requestParams;

        if (userContext.getRole() != UserRole.ADMIN)
            response = "You can't add books to the library";
        else {
            requestParams = request.split(paramSeparator);
            if (requestParams.length != 3)
                response = wrongSignature;
            else {
                title = requestParams[0];
                author = requestParams[1];
                genre = requestParams[2];

                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                LibraryService libraryService = serviceFactory.getLibraryService();

                try {
                    Book newBook = new Book(title, author, BookGenre.valueOf(genre.toUpperCase()));
                    if (libraryService.addNewBook(newBook))
                        response = "Book added to the library!";
                    else
                        response = "Invalid input parameters!";
                } catch (ServiceException | IllegalArgumentException e) {
                    response = "Error during book adding";
                    Log.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}
