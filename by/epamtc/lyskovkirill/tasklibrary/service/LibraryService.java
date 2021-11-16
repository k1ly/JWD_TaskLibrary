package by.epamtc.lyskovkirill.tasklibrary.service;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.service.exception.ServiceException;

import java.util.List;

public interface LibraryService {

    boolean addNewBook(Book book) throws ServiceException;
    boolean editBook(String title, String author, String editingAttribute, String newAttribute) throws ServiceException;
    boolean deleteBook(String title, String author) throws ServiceException;
    Book findByTitleAndAuthor(String title, String author) throws ServiceException;
    List<Book> findByAttribute(String attribute, String searchingFilter) throws ServiceException;
    List<Book> findBooks() throws ServiceException;
    List<Book> findFavourites(User user) throws ServiceException;
}
