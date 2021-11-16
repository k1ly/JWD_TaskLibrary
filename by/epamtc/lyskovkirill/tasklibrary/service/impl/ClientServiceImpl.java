package by.epamtc.lyskovkirill.tasklibrary.service.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserAttribute;
import by.epamtc.lyskovkirill.tasklibrary.dao.UserDAO;
import by.epamtc.lyskovkirill.tasklibrary.dao.exception.DAOException;
import by.epamtc.lyskovkirill.tasklibrary.dao.factory.DAOFactory;
import by.epamtc.lyskovkirill.tasklibrary.service.ClientService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.hash.SHA256PasswordHash;
import by.epamtc.lyskovkirill.tasklibrary.service.validation.UserValidation;

public class ClientServiceImpl implements ClientService {

    @Override
    public User signIn(String login, String password) throws ServiceException {
        User user = null;
        UserValidation userValidation = new UserValidation();

        if (userValidation.isLoginValid(login) && userValidation.isPasswordValid(password)) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoObjectFactory.getUserDAO();
            SHA256PasswordHash hash = new SHA256PasswordHash();

            try {
                password = hash.computeHash(password);
                user = userDAO.logInUser(login, password);
            } catch (ServiceException | DAOException e) {
                throw new ServiceException("Sign in error", e);
            }
        }
        return user;
    }

    @Override
    public User signOut(User user) throws ServiceException {
        if (user.getLogin() == null)
            throw new ServiceException("Sign out error");
        user = User.getGuestInstance();
        return user;
    }

    @Override
    public User registerUser(User user) throws ServiceException {
        UserValidation userValidation = new UserValidation();

        if (userValidation.isLoginValid(user.getLogin()) && userValidation.isPasswordValid(user.getPassword())
                && userValidation.isNameValidName(user.getName())) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoObjectFactory.getUserDAO();
            SHA256PasswordHash hash = new SHA256PasswordHash();

            try {
                user.setPassword(hash.computeHash(user.getPassword()));
                userDAO.registerUser(user);
            } catch (ServiceException | DAOException e) {
                throw new ServiceException("User registration error", e);
            }
        } else
            user = null;
        return user;
    }

    @Override
    public User updateUser(User user, String password, String updatingAttribute, String newAttribute) throws ServiceException {
        boolean isNewAttributeValid = false;
        UserAttribute userAttribute;

        UserValidation userValidation = new UserValidation();
        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoObjectFactory.getUserDAO();
        SHA256PasswordHash hash = new SHA256PasswordHash();

        try {
            userAttribute = UserAttribute.valueOf(updatingAttribute);
            switch (userAttribute) {
                case LOGIN -> isNewAttributeValid = userValidation.isLoginValid(newAttribute);
                case PASSWORD -> isNewAttributeValid = userValidation.isPasswordValid(newAttribute);
                case NAME -> isNewAttributeValid = userValidation.isNameValidName(newAttribute);
            }
            password = hash.computeHash(password);
            if (userAttribute == UserAttribute.PASSWORD)
                newAttribute = hash.computeHash(newAttribute);
            if (isNewAttributeValid && userValidation.isPasswordValid(password))
                user = userDAO.updateUser(user, password, userAttribute, newAttribute);
            else
                user = null;
        } catch (ServiceException | DAOException e) {
            throw new ServiceException("User updating error", e);
        }
        return user;
    }

    @Override
    public User deleteUser(String login, String password) throws ServiceException {
        User user = null;
        UserValidation userValidation = new UserValidation();

        if (userValidation.isPasswordValid(password)) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoObjectFactory.getUserDAO();
            SHA256PasswordHash hash = new SHA256PasswordHash();

            try {
                password = hash.computeHash(password);
                user = userDAO.deleteUser(login, password);
            } catch (ServiceException | DAOException e) {
                throw new ServiceException("User deleting error", e);
            }
        }
        return user;
    }

    @Override
    public User addToFavourites(User user, Integer bookId) throws ServiceException {
        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoObjectFactory.getUserDAO();

        try {
            user = userDAO.addToFavourites(user, bookId);
        } catch (DAOException e) {
            throw new ServiceException("Adding book to favourites error", e);
        }
        return user;
    }

    @Override
    public User removeFromFavourites(User user, Integer bookId) throws ServiceException {
        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoObjectFactory.getUserDAO();

        try {
            user = userDAO.removeFromFavourites(user, bookId);
        } catch (DAOException e) {
            throw new ServiceException("Removing book from favourites error", e);
        }
        return user;
    }
}
