package by.epamtc.lyskovkirill.tasklibrary.service.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.User;
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

        if (UserValidation.validateLogin(login) && UserValidation.validatePassword(password)) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoObjectFactory.getUserDAO();

            try {
                password = SHA256PasswordHash.computeHash(password);
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
        user = new User();
        return user;
    }

    @Override
    public User register(User user) throws ServiceException {
        if (UserValidation.validateLogin(user.getLogin()) && UserValidation.validatePassword(user.getPassword())
                && UserValidation.validateName(user.getName())) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoObjectFactory.getUserDAO();

            try {
                user.setPassword(SHA256PasswordHash.computeHash(user.getPassword()));
                userDAO.registerUser(user);
            } catch (ServiceException | DAOException e) {
                throw new ServiceException("User registration error", e);
            }
        }
        return user;
    }

    @Override
    public User update(User user, String password, String updatingAttribute, String newAttribute) throws ServiceException {
        boolean isAttributeValid = switch (updatingAttribute) {
            case "LOGIN" -> UserValidation.validateLogin(newAttribute);
            case "PASSWORD" -> UserValidation.validatePassword(newAttribute);
            case "NAME" -> UserValidation.validateName(newAttribute);
            default -> throw new ServiceException("Wrong user attribute");
        };

        if (isAttributeValid && UserValidation.validatePassword(password)) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoObjectFactory.getUserDAO();

            try {
                password = SHA256PasswordHash.computeHash(password);
                if (updatingAttribute.equals("PASSWORD"))
                    newAttribute = SHA256PasswordHash.computeHash(newAttribute);
                user = userDAO.updateUser(user, password, updatingAttribute, newAttribute);
            } catch (ServiceException | DAOException e) {
                throw new ServiceException("User updating error", e);
            }
        }
        return user;
    }

    @Override
    public User delete(String login, String password) throws ServiceException {
        User user = null;

        if (UserValidation.validatePassword(password)) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoObjectFactory.getUserDAO();

            try {
                password = SHA256PasswordHash.computeHash(password);
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
