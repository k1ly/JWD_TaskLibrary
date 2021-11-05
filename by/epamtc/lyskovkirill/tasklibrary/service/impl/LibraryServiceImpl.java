package by.epamtc.lyskovkirill.tasklibrary.service.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
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
    public void addNewBook(Book book) throws ServiceException {
        if (BookValidation.validateTitle(book.getTitle()) && BookValidation.validateAuthor(book.getAuthor())) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            BookDAO bookDAO = daoObjectFactory.getBookDAO();

            try {
                bookDAO.addBook(book);
            } catch (DAOException e) {
                throw new ServiceException("Book adding error", e);
            }
        }
    }

    @Override
    public void editBook(String title, String author, String editingAttribute, String newAttribute) throws ServiceException {
        boolean isAttributeValid = switch (editingAttribute) {
            case "TITLE" -> BookValidation.validateTitle(newAttribute);
            case "AUTHOR" -> BookValidation.validateAuthor(newAttribute);
            case "GENRE" -> BookValidation.validateGenre(newAttribute.toUpperCase());
            default -> throw new ServiceException("Wrong book attribute");
        };

        if (isAttributeValid && BookValidation.validateTitle(title) && BookValidation.validateAuthor(author)) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            BookDAO bookDAO = daoObjectFactory.getBookDAO();

            try {
                bookDAO.editBook(title, author, editingAttribute, newAttribute);
            } catch (DAOException e) {
                throw new ServiceException("Book editing error", e);
            }
        }
    }

    @Override
    public void deleteBook(String title, String author) throws ServiceException {
        if (BookValidation.validateTitle(title) && BookValidation.validateAuthor(author)) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            BookDAO bookDAO = daoObjectFactory.getBookDAO();

            try {
                bookDAO.deleteBook(title, author);
            } catch (DAOException e) {
                throw new ServiceException("Book deleting error", e);
            }
        }
    }

    @Override
    public Book findByTitleAndAuthor(String title, String author) throws ServiceException {
        Book book = null;

        if (BookValidation.validateTitle(title) && BookValidation.validateAuthor(author)) {
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

        boolean isAttributeValid = switch (attribute) {
            case "TITLE" -> BookValidation.validateTitle(searchingFilter);
            case "AUTHOR" -> BookValidation.validateAuthor(searchingFilter);
            case "GENRE" -> BookValidation.validateGenre(searchingFilter.toUpperCase());
            default -> throw new ServiceException("Wrong book attribute");
        };

        if (isAttributeValid) {
            DAOFactory daoObjectFactory = DAOFactory.getInstance();
            BookDAO bookDAO = daoObjectFactory.getBookDAO();

            try {
                bookList = bookDAO.findByAttribute(attribute, searchingFilter);
            } catch (DAOException e) {
                throw new ServiceException("Book deleting error", e);
            }
        }
        return bookList;
    }

    @Override
    public List<Book> showAllBooks() throws ServiceException {
        List<Book> bookList;

        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        BookDAO bookDAO = daoObjectFactory.getBookDAO();

        try {
            bookList = bookDAO.showBooks();
        } catch (DAOException e) {
            throw new ServiceException("Showing all books error", e);
        }
        return bookList;
    }

    @Override
    public List<Book> showFavourites(User user) throws ServiceException {
        List<Book> bookList;

        DAOFactory daoObjectFactory = DAOFactory.getInstance();
        BookDAO bookDAO = daoObjectFactory.getBookDAO();

        try {
            bookList = bookDAO.showFavourites(user);
        } catch (DAOException e) {
            throw new ServiceException("Showing user favourites error", e);
        }
        return bookList;
    }
}
