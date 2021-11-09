package by.epamtc.lyskovkirill.tasklibrary.dao.impl;

import by.epamtc.lyskovkirill.tasklibrary.bean.Book;
import by.epamtc.lyskovkirill.tasklibrary.bean.BookAttribute;
import by.epamtc.lyskovkirill.tasklibrary.bean.BookGenre;
import by.epamtc.lyskovkirill.tasklibrary.bean.User;
import by.epamtc.lyskovkirill.tasklibrary.dao.BookDAO;
import by.epamtc.lyskovkirill.tasklibrary.dao.exception.DAOException;
import by.epamtc.lyskovkirill.tasklibrary.util.FilePathConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class TxtBookDAO implements BookDAO {
    private final static String booksFilePath = "resources/books.txt";
    private final static String attributeSeparator = "\t";

    @Override
    public void addBook(Book book) throws DAOException {
        List<Book> bookList;

        try {
            bookList = scanBooksFromFile();

            for (Book b : bookList) {
                if (book.getTitle().equals(b.getTitle()) && book.getAuthor().equals(b.getAuthor()))
                    throw new DAOException("Library already contains this book");
            }

            if (bookList.size() > 0) {
                Book.setUID(bookList.get(bookList.size() - 1).getId());
                book.setId(Book.getUID());
            }
            bookList.add(book);

            writeUsersToFile(bookList);
        } catch (Exception e) {
            throw new DAOException("Book adding process error", e);
        }
    }

    @Override
    public void editBook(String title, String author, BookAttribute editingAttribute, String newAttribute) throws DAOException {
        List<Book> bookList;

        try {
            bookList = scanBooksFromFile();

            for (int i = 0; i < bookList.size(); i++) {
                if (title.equals(bookList.get(i).getTitle()) && author.equals(bookList.get(i).getAuthor())) {
                    Book book = bookList.get(i);
                    switch (editingAttribute) {
                        case TITLE -> book.setTitle(newAttribute);
                        case AUTHOR -> book.setAuthor(newAttribute);
                        case GENRE -> book.setGenre(BookGenre.valueOf(newAttribute.toUpperCase()));
                    }
                    bookList.set(i, book);
                }

            }
            writeUsersToFile(bookList);
        } catch (Exception e) {
            throw new DAOException("Book editing process error", e);
        }
    }

    @Override
    public void deleteBook(String title, String author) throws DAOException {
        List<Book> bookList;

        try {
            bookList = scanBooksFromFile();

            for (int i = 0; i < bookList.size(); i++) {
                if (title.equals(bookList.get(i).getTitle()) && author.equals(bookList.get(i).getAuthor())) {
                    bookList.remove(i);
                    break;
                }
            }
            writeUsersToFile(bookList);
        } catch (Exception e) {
            throw new DAOException("Book deleting process error", e);
        }
    }

    @Override
    public Book findByTitleAndAuthor(String title, String author) throws DAOException {
        List<Book> bookList;
        Book book = null;

        try {
            bookList = scanBooksFromFile();

            for (Book b : bookList) {
                if (title.equals(b.getTitle()) && author.equals(b.getAuthor()))
                    book = b;
            }
        } catch (Exception e) {
            throw new DAOException("Searching book to favourites process error", e);
        }
        return book;
    }

    @Override
    public List<Book> findByAttribute(BookAttribute attribute, String searchingFilter) throws DAOException {
        List<Book> bookList;

        try {
            bookList = selectBooksByPredicate(scanBooksFromFile(), book ->
                    ((attribute == BookAttribute.TITLE && searchingFilter.equals(book.getTitle()))
                            || (attribute == BookAttribute.AUTHOR && searchingFilter.equals(book.getAuthor()))
                            || (attribute == BookAttribute.GENRE && searchingFilter.toUpperCase().equals(book.getGenre().toString()))));
        } catch (Exception e) {
            throw new DAOException("Searching books by attribute process error", e);
        }
        return bookList;
    }

    @Override
    public List<Book> showBooks() throws DAOException {
        List<Book> bookList;

        try {
            bookList = scanBooksFromFile();
        } catch (Exception e) {
            throw new DAOException("Receiving all books process error", e);
        }
        return bookList;
    }

    @Override
    public List<Book> showFavourites(User user) throws DAOException {
        List<Book> bookList;
        List<Book> favourites = new ArrayList<>();

        try {
            bookList = scanBooksFromFile();
            for (int i = 0; i < user.getBooks().size(); i++) {
                Integer bookId = user.getBooks().get(i);
                List<Book> books = selectBooksByPredicate(bookList, book ->
                        bookId == book.getId());
                if (books.size() > 0)
                    favourites.add(books.get(0));
            }
        } catch (Exception e) {
            throw new DAOException("Receiving user favourites error", e);
        }
        return favourites;
    }

    private List<Book> scanBooksFromFile() throws DAOException, IOException, NumberFormatException {
        File booksFile = FilePathConstructor.computeFilePath(new File(System.getProperty("user.dir")), booksFilePath);
        if (booksFile == null)
            throw new DAOException("Opening source file error");

        try (Scanner scanner = new Scanner(booksFile)) {
            List<Book> bookList = new ArrayList<>();

            while (scanner.hasNext()) {
                String[] bookAttributes = scanner.nextLine().split(attributeSeparator);
                Book temp = new Book();
                temp.setId(Integer.parseInt(bookAttributes[0]));
                temp.setTitle(bookAttributes[1]);
                temp.setAuthor(bookAttributes[2]);
                temp.setGenre(BookGenre.valueOf(bookAttributes[3]));
                bookList.add(temp);
            }
            return bookList;
        }
    }

    private List<Book> selectBooksByPredicate(List<Book> bookList, Predicate<Book> predicate) {
        List<Book> newList = new ArrayList<>();

        for (Book book : bookList)
            if (predicate.test(book))
                newList.add(book);
        return newList;
    }

    private void writeUsersToFile(List<Book> books) throws DAOException, IOException {
        File booksFile = FilePathConstructor.computeFilePath(new File(System.getProperty("user.dir")), booksFilePath);
        if (booksFile == null)
            throw new DAOException("Opening source file error");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(booksFile, false))) {

            for (Book b : books) {
                writer.append(String.valueOf(b.getId())).append(attributeSeparator)
                        .append(b.getTitle()).append(attributeSeparator)
                        .append(b.getAuthor()).append(attributeSeparator)
                        .append(b.getGenre().toString()).append('\n');
            }
        }
    }
}
