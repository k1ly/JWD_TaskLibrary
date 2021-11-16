package by.epamtc.lyskovkirill.tasklibrary.service.factory;

import by.epamtc.lyskovkirill.tasklibrary.service.ClientService;
import by.epamtc.lyskovkirill.tasklibrary.service.LibraryService;
import by.epamtc.lyskovkirill.tasklibrary.service.impl.ClientServiceImpl;
import by.epamtc.lyskovkirill.tasklibrary.service.impl.LibraryServiceImpl;

public class ServiceFactory {
    private static ServiceFactory instance;

    private final ClientService clientService = new ClientServiceImpl();
    private final LibraryService libraryService = new LibraryServiceImpl();

    public static synchronized ServiceFactory getInstance() {
        if (instance == null)
            instance = new ServiceFactory();
        return instance;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public LibraryService getLibraryService() {
        return libraryService;
    }
}
