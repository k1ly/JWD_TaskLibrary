package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.LibraryLogger;
import by.epamtc.lyskovkirill.tasklibrary.service.ClientService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

public class UpdateUser extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        String updatingAttribute;
        String newAttribute;
        String password;
        String[] requestParams;

        if (userContext.getRole() == UserRole.GUEST)
            response = "You aren't logged in";
        else {
            requestParams = request.split(paramSeparator);
            if (requestParams.length != 3)
                response = wrongSignature;
            else {
                updatingAttribute = requestParams[0].toUpperCase();
                newAttribute = requestParams[1];
                password = requestParams[2];

                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                ClientService clientService = serviceFactory.getClientService();

                try {
                    User user = clientService.updateUser(userContext, password, updatingAttribute.toUpperCase(), newAttribute);
                    if (user == null)
                        response = "Invalid input parameters!";
                    else {
                        userContext = user;
                        response = "User successfully updated!";
                    }
                } catch (ServiceException e) {
                    response = "Error during user updating";
                    LibraryLogger.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}
