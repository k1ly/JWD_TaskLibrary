package by.epamtc.lyskovkirill.tasklibrary.controller.command.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.controller.command.AbstractCommand;
import by.epamtc.lyskovkirill.tasklibrary.controller.logger.Log;
import by.epamtc.lyskovkirill.tasklibrary.service.ClientService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.factory.ServiceFactory;

public class DeleteUser extends AbstractCommand {

    @Override
    public String execute(String request) {
        String response;

        String password;
        String[] requestParams;

        if (userContext.getRole() == UserRole.GUEST)
            response = "You aren't logged in";
        else {
            requestParams = request.split(paramSeparator);
            if (requestParams.length != 1)
                response = wrongSignature;
            else {
                password = requestParams[0];

                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                ClientService clientService = serviceFactory.getClientService();

                try {
                    userContext = clientService.delete(userContext.getLogin(), password);
                    response = "User deleted";
                } catch (ServiceException e) {
                    response = "Error during user deleting";
                    Log.getLogger().error("Error stack trace:", e);
                }
            }
        }
        return response;
    }
}
