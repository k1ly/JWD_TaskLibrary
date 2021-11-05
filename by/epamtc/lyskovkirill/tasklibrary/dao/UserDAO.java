package by.epamtc.lyskovkirill.tasklibrary.dao;

import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.dao.exception.DAOException;

public interface UserDAO {

    User logInUser(String login, String password) throws DAOException;
    void registerUser(User newUser) throws DAOException;
    User updateUser(User user, String password, String updatingAttribute, String newAttribute) throws DAOException;
    User deleteUser(String login, String password) throws DAOException;
    User addToFavourites(User user, Integer bookId) throws DAOException;
    User removeFromFavourites(User user, Integer bookId) throws DAOException;
}
