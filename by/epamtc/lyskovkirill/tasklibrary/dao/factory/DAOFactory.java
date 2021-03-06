package by.epamtc.lyskovkirill.tasklibrary.dao.factory;

import by.epamtc.lyskovkirill.tasklibrary.dao.BookDAO;
import by.epamtc.lyskovkirill.tasklibrary.dao.UserDAO;
import by.epamtc.lyskovkirill.tasklibrary.dao.impl.TxtBookDAO;
import by.epamtc.lyskovkirill.tasklibrary.dao.impl.TxtUserDAO;

public class DAOFactory {
    private static DAOFactory instance;

    private UserDAO userDAO = new TxtUserDAO();
    private BookDAO bookDAO = new TxtBookDAO();

    public static synchronized  DAOFactory getInstance() {
        if (instance == null)
            instance = new DAOFactory();
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public BookDAO getBookDAO() {
        return bookDAO;
    }

    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
}
