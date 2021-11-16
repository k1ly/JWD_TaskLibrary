package by.epamtc.lyskovkirill.tasklibrary.service.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.BookAttribute;
import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.dao.BookDAO;
import by.epamtc.lyskovkirill.tasklibrary.dao.exception.DAOException;
import by.epamtc.lyskovkirill.tasklibrary.dao.factory.DAOFactory;
import by.epamtc.lyskovkirill.tasklibrary.service.LibraryService;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;
import by.epamtc.lyskovkirill.tasklibrary.service.validation.BookValidation;

import java.util.List;

public class LibraryServiceImpl implements LibraryService {

    @Override
    public boolean addNewBook(Book book) throws ServiceException {
        boolean isInputValid = false;
        BookValidation bookValidation = new BookValidation();

        if (bookValidation.isTitleValid(book.getTitle()) && bookValidation.isAuthorValid(book.getAuthor())) {
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

        BookValidation bookValidation = new BookValidation();
        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        BookDAO bookDAO = daoObjectFactory.getBookDAO();

        try {
            bookAttribute = BookAttribute.valueOf(editingAttribute);
            switch (bookAttribute) {
                case TITLE -> isNewAttributeValid = bookValidation.isTitleValid(newAttribute);
                case AUTHOR -> isNewAttributeValid = bookValidation.isAuthorValid(newAttribute);
                case GENRE -> isNewAttributeValid = bookValidation.isGenreValid(newAttribute);
            }
            if (isNewAttributeValid && bookValidation.isTitleValid(title) && bookValidation.isAuthorValid(author)) {
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
        BookValidation bookValidation = new BookValidation();

        if (bookValidation.isTitleValid(title) && bookValidation.isAuthorValid(author)) {
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
        BookValidation bookValidation = new BookValidation();

        if (bookValidation.isTitleValid(title) && bookValidation.isAuthorValid(author)) {
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

        BookValidation bookValidation = new BookValidation();
        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        BookDAO bookDAO = daoObjectFactory.getBookDAO();

        try {
            bookAttribute = BookAttribute.valueOf(attribute);
            switch (bookAttribute) {
                case TITLE -> isSearchingFilterValid = bookValidation.isTitleValid(searchingFilter);
                case AUTHOR -> isSearchingFilterValid = bookValidation.isAuthorValid(searchingFilter);
                case GENRE -> isSearchingFilterValid = bookValidation.isGenreValid(searchingFilter);
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
