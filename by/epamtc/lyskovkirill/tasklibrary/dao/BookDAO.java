package by.epamtc.lyskovkirill.tasklibrary.dao;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.BookAttribute;
import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.dao.exception.DAOException;

import java.util.List;

public interface BookDAO {

    void addBook(Book book) throws DAOException;
    void editBook(String name, String author, BookAttribute editingAttribute, String newAttribute) throws DAOException;
    void deleteBook(String title, String author) throws DAOException;
    Book findByTitleAndAuthor(String title, String author) throws DAOException;
    List<Book> findByAttribute(BookAttribute attribute, String searchingFilter) throws DAOException;
    List<Book> findBooks() throws DAOException;
    List<Book> findFavourites(User user) throws DAOException;
}
