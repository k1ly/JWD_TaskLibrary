package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.LibraryLogger;
import by.epamtc.lyskovkirill.tasklibrary.service.ClientService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

public class RegisterUser extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        String login;
        String password;
        String name;
        String[] requestParams;

        if (userContext.getRole() != UserRole.GUEST)
            response = "You are already logged in";
        else {
            requestParams = request.split(paramSeparator);
            if (requestParams.length != 3)
                response = wrongSignature;
            else {
                login = requestParams[0];
                password = requestParams[1];
                name = requestParams[2];

                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                ClientService clientService = serviceFactory.getClientService();

                try {
                    User user = clientService.registerUser(new User(login, password, name));
                    if (user == null)
                        response = "Invalid input parameters!";
                    else {
                        userContext = user;
                        response = "Successfully registered!";
                    }
                } catch (ServiceException e) {
                    response = "Error during registration procedure";
                    LibraryLogger.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}
