package by.epamtc.lyskovkirill.tasklibrary.service.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.BookAttribute;
import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.dao.BookDAO;
import by.epamtc.lyskovkirill.tasklibrary.dao.exception.DAOException;
import by.epamtc.lyskovkirill.tasklibrary.dao.factory.DAOFactory;
import by.epamtc.lyskovkirill.tasklibrary.service.LibraryService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.validation.BookValidator;

import java.util.List;

public class LibraryServiceImpl implements LibraryService {

    @Override
    public boolean addNewBook(Book book) throws ServiceException {
        boolean isInputValid = false;
        BookValidator bookValidator = new BookValidator();

        if (bookValidator.isTitleValid(book.getTitle()) && bookValidator.isAuthorValid(book.getAuthor())) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            BookDAO bookDAO = daoObjectFactory.getBookDAO();

            try {
                bookDAO.addBook(book);
                isInputValid = true;
            } catch (DAOException e) {
                throw new ServiceException("Book adding error", e);
            }
        }
        return isInputValid;
    }

    @Override
    public boolean editBook(String title, String author, String editingAttribute, String newAttribute) throws ServiceException {
        boolean isInputValid = false;
        boolean isNewAttributeValid = false;
        BookAttribute bookAttribute;

        BookValidator bookValidator = new BookValidator();
        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        BookDAO bookDAO = daoObjectFactory.getBookDAO();

        try {
            bookAttribute = BookAttribute.valueOf(editingAttribute);
            switch (bookAttribute) {
                case TITLE -> isNewAttributeValid = bookValidator.isTitleValid(newAttribute);
                case AUTHOR -> isNewAttributeValid = bookValidator.isAuthorValid(newAttribute);
                case GENRE -> isNewAttributeValid = bookValidator.isGenreValid(newAttribute);
            }
            if (isNewAttributeValid && bookValidator.isTitleValid(title) && bookValidator.isAuthorValid(author)) {
                bookDAO.editBook(title, author, bookAttribute, newAttribute);
                isInputValid = true;
            }
        } catch (DAOException e) {
            throw new ServiceException("Book editing error", e);
        }
        return isInputValid;
    }

    @Override
    public boolean deleteBook(String title, String author) throws ServiceException {
        boolean isInputValid = false;
        BookValidator bookValidator = new BookValidator();

        if (bookValidator.isTitleValid(title) && bookValidator.isAuthorValid(author)) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            BookDAO bookDAO = daoObjectFactory.getBookDAO();

            try {
                bookDAO.deleteBook(title, author);
                isInputValid = true;
            } catch (DAOException e) {
                throw new ServiceException("Book deleting error", e);
            }
        }
        return isInputValid;
    }

    @Override
    public Book findByTitleAndAuthor(String title, String author) throws ServiceException {
        Book book = null;
        BookValidator bookValidator = new BookValidator();

        if (bookValidator.isTitleValid(title) && bookValidator.isAuthorValid(author)) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            BookDAO bookDAO = daoObjectFactory.getBookDAO();

            try {
                book = bookDAO.findByTitleAndAuthor(title, author);
            } catch (DAOException e) {
                throw new ServiceException("Searching book to favourites error", e);
            }
        }
        return book;
    }

    @Override
    public List<Book> findByAttribute(String attribute, String searchingFilter) throws ServiceException {
        List<Book> bookList = null;
        boolean isSearchingFilterValid = false;
        BookAttribute bookAttribute;

        BookValidator bookValidator = new BookValidator();
        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        BookDAO bookDAO = daoObjectFactory.getBookDAO();

        try {
            bookAttribute = BookAttribute.valueOf(attribute);
            switch (bookAttribute) {
                case TITLE -> isSearchingFilterValid = bookValidator.isTitleValid(searchingFilter);
                case AUTHOR -> isSearchingFilterValid = bookValidator.isAuthorValid(searchingFilter);
                case GENRE -> isSearchingFilterValid = bookValidator.isGenreValid(searchingFilter);
            }
            if (isSearchingFilterValid)
                bookList = bookDAO.findByAttribute(bookAttribute, searchingFilter);
        } catch (DAOException | IllegalArgumentException e) {
            throw new ServiceException("Book deleting error", e);
        }
        return bookList;
    }

    @Override
    public List<Book> findBooks() throws ServiceException {
        List<Book> bookList;

        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        BookDAO bookDAO = daoObjectFactory.getBookDAO();

        try {
            bookList = bookDAO.findBooks();
        } catch (DAOException e) {
            throw new ServiceException("Showing all books error", e);
        }
        return bookList;
    }

    @Override
    public List<Book> findFavourites(User user) throws ServiceException {
        List<Book> bookList;

        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        BookDAO bookDAO = daoObjectFactory.getBookDAO();

        try {
            bookList = bookDAO.findFavourites(user);
        } catch (DAOException e) {
            throw new ServiceException("Showing user favourites error", e);
        }
        return bookList;
    }
}
