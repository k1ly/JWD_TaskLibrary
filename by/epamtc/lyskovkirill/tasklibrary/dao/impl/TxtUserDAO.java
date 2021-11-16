package by.epamtc.lyskovkirill.tasklibrary.dao.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserAttribute;
import by.epamtc.lyskovkirill.tasklibrary.bean.UserRole;
import by.epamtc.lyskovkirill.tasklibrary.dao.UserDAO;
import by.epamtc.lyskovkirill.tasklibrary.dao.exception.DAOException;
import by.epamtc.lyskovkirill.tasklibrary.util.FilePathConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TxtUserDAO implements UserDAO {
    private final static String usersFilePath = "resources/users.txt";
    private final static String attributeSeparator = "\t";
    private final static String bookIdSeparator = ", ";

    @Override
    public User logInUser(String login, String password) throws DAOException {
        User user = User.getGuestInstance();
        List<User> userList;

        try {
            userList = scanUsersFromFile();

            for (User value : userList)
                if (login.equals(value.getLogin()) && password.equals(value.getPassword()))
                    user = value;

        } catch (DAOException | IOException | NumberFormatException e) {
            throw new DAOException("Authorization process error", e);
        }
        return user;
    }

    @Override
    public void registerUser(User newUser) throws DAOException {
        List<User> userList;

        try {
            userList = scanUsersFromFile();

            for (User u : userList) {
                if (newUser.getLogin().equals(u.getLogin()))
                    throw new DAOException("This login is already in use");
            }

            if (userList.size() > 0) {
                User.setUID(userList.get(userList.size() - 1).getId());
                newUser.setId(User.getUID());
            }
            userList.add(newUser);

            writeUsersToFile(userList);
        } catch (DAOException | IOException | NumberFormatException e) {
            throw new DAOException("Registration process error", e);
        }
    }

    @Override
    public User updateUser(User user, String password, UserAttribute updatingAttribute, String newAttribute) throws DAOException {
        List<User> userList;

        try {
            userList = scanUsersFromFile();

            for (User u : userList) {
                if (updatingAttribute == UserAttribute.LOGIN
                        && !user.getLogin().equals(newAttribute) && newAttribute.equals(u.getLogin()))
                    throw new DAOException("Login is already in use");
            }

            for (int i = 0; i < userList.size(); i++) {
                if (user.getLogin().equals(userList.get(i).getLogin())) {
                    if (!password.equals(userList.get(i).getPassword()))
                        throw new DAOException("Wrong password");
                    switch (updatingAttribute) {
                        case LOGIN -> user.setLogin(newAttribute);
                        case PASSWORD -> user.setPassword(newAttribute);
                        case NAME -> user.setName(newAttribute);
                    }
                    userList.set(i, user);
                }
            }
            writeUsersToFile(userList);
        } catch (DAOException | IOException | NumberFormatException e) {
            throw new DAOException("User updating process error", e);
        }
        return user;
    }

    @Override
    public User deleteUser(String login, String password) throws DAOException {
        User user = User.getGuestInstance();
        List<User> userList;

        try {
            userList = scanUsersFromFile();

            for (int i = 0; i < userList.size(); i++) {
                if (login.equals(userList.get(i).getLogin())) {
                    if (!password.equals(userList.get(i).getPassword()))
                        throw new DAOException("Wrong password");
                    userList.remove(i);
                    break;
                }
            }
            writeUsersToFile(userList);
        } catch (DAOException | IOException | NumberFormatException | IndexOutOfBoundsException e) {
            throw new DAOException("User deleting process error", e);
        }
        return user;
    }

    @Override
    public User addToFavourites(User user, Integer bookId) throws DAOException {
        List<User> userList;

        try {
            userList = scanUsersFromFile();

            for (int i = 0; i < userList.size(); i++) {
                if (user.getLogin().equals(userList.get(i).getLogin()) && !user.getBooks().contains(bookId)) {
                    user.getBooks().add(bookId);
                    userList.set(i, user);
                }
            }
            writeUsersToFile(userList);
        } catch (DAOException | IOException | NumberFormatException e) {
            throw new DAOException("Adding book to favourites process error", e);
        }
        return user;
    }

    @Override
    public User removeFromFavourites(User user, Integer bookId) throws DAOException {
        List<User> userList;

        try {
            userList = scanUsersFromFile();

            for (int i = 0; i < userList.size(); i++) {
                if (user.getLogin().equals(userList.get(i).getLogin()) && user.getBooks().contains(bookId)) {
                    user.getBooks().remove(bookId);
                    userList.set(i, user);
                }
            }
            writeUsersToFile(userList);
        } catch (DAOException | IOException | NumberFormatException e) {
            throw new DAOException("Removing book from favourites process error", e);
        }
        return user;
    }

    private List<User> scanUsersFromFile() throws DAOException, IOException, NumberFormatException {
        FilePathConstructor filePathConstructor = FilePathConstructor.getInstance();
        File usersFile = filePathConstructor.computeFilePath(new File(System.getProperty("user.dir")), usersFilePath);
        if (usersFile == null)
            throw new DAOException("Opening source file error");

        try (Scanner scanner = new Scanner(usersFile)) {
            List<User> userList = new ArrayList<>();

            while (scanner.hasNext()) {
                String[] userAttributes = scanner.nextLine().split(attributeSeparator);
                User temp = new User();
                temp.setId(Integer.parseInt(userAttributes[0]));
                temp.setLogin(userAttributes[1]);
                temp.setPassword(userAttributes[2]);
                temp.setName(userAttributes[3]);
                temp.setRole(UserRole.valueOf(userAttributes[4]));

                if (userAttributes.length > 5) {
                    List<Integer> bookIdList = new ArrayList<>();
                    String[] userBookIds = userAttributes[5].split(bookIdSeparator);
                    for (String userBookId : userBookIds)
                        bookIdList.add(Integer.parseInt(userBookId));
                    temp.setBooks(bookIdList);
                }
                userList.add(temp);
            }
            return userList;
        }
    }

    private void writeUsersToFile(List<User> users) throws DAOException, IOException {
        FilePathConstructor filePathConstructor = FilePathConstructor.getInstance();
        File usersFile = filePathConstructor.computeFilePath(new File(System.getProperty("user.dir")), usersFilePath);
        if (usersFile == null)
            throw new DAOException("Opening source file error");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile, false))) {

            for (User user : users) {
                writer.append(String.valueOf(user.getId())).append(attributeSeparator)
                        .append(user.getLogin()).append(attributeSeparator)
                        .append(user.getPassword()).append(attributeSeparator)
                        .append(user.getName()).append(attributeSeparator)
                        .append(user.getRole().toString());

                if (user.getBooks().size() > 0) {
                    writer.append(attributeSeparator);
                    for (int i = 0; i < user.getBooks().size(); i++) {
                        writer.append(user.getBooks().get(i).toString());
                        if (i < user.getBooks().size() - 1)
                            writer.append(bookIdSeparator);
                    }
                }
                writer.append('\n');
            }
        }
    }
}
