package by.epamtc.lyskovkirill.tasklibrary.bean;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private static int UID;

    private int id;

    private String title;

    private String author;

    private BookGenre genre;

    public Book() {}

    public Book(String title, String author, BookGenre genre){
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public static int getUID() {
        return UID;
    }

    public static void setUID(int UID) {
        Book.UID = ++UID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(title, book.title) && Objects.equals(author, book.author) && genre == book.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre);
    }

    @Override
    public String toString() {
        return title + ", " + author + " (" + genre + ')';
    }
}
