package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.LibraryLogger;
import by.epamtc.lyskovkirill.tasklibrary.service.ClientService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

public class SignOut extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        if (userContext.getRole() == UserRole.GUEST)
            response = "You aren't logged in";
        else {
            if (request.length() > 0)
                response = wrongSignature;
            else {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                ClientService clientService = serviceFactory.getClientService();

                try {
                    userContext = clientService.signOut(userContext);
                    response = "You have logged out";
                } catch (ServiceException e) {
                    response = "Error during logout procedure";
                    LibraryLogger.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}
