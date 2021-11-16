package by.epamtc.lyskovkirill.tasklibrary.service;

import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;

public interface ClientService {

    User signIn(String login, String password) throws ServiceException;
    User signOut(User user) throws ServiceException;
    User registerUser(User user) throws ServiceException;
    User updateUser(User user, String password, String updatingAttribute, String newAttribute) throws ServiceException;
    User deleteUser(String login, String password) throws ServiceException;
    User addToFavourites(User user, Integer bookId) throws ServiceException;
    User removeFromFavourites(User user, Integer bookId) throws ServiceException;
}
