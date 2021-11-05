package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.Log;
import by.epamtc.lyskovkirill.tasklibrary.service.LibraryService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

public class DeleteBook extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        String title;
        String author;
        String[] requestParams;

        if (userContext.getRole() != UserRole.ADMIN)
            response = "You can't delete books in the library";
        else {
            requestParams = request.split(paramSeparator);
            if (requestParams.length != 2)
                response = wrongSignature;
            else {
                title = requestParams[0];
                author = requestParams[1];

                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                LibraryService libraryService = serviceFactory.getLibraryService();

                try {
                    libraryService.deleteBook(title, author);
                    response = "Book deleted";
                } catch (ServiceException e) {
                    response = "Error during book deleting";
                    Log.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}
