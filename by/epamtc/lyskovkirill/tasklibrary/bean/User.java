package by.epamtc.lyskovkirill.tasklibrary.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private static int UID;

    private int id;

    private String login;

    private String password;

    private String name;

    private UserRole role;

    private List<Integer> bookIds = new ArrayList<>();

    public User() {
        role = UserRole.GUEST;
    }

    public User(String login, String password, String name){
        this.login = login;
        this.password = password;
        this.name = name;
        role = UserRole.USER;
    }

    public static int getUID() {
        return UID;
    }

    public static void setUID(int UID) {
        User.UID = ++UID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Integer> getBooks() {
        return bookIds;
    }

    public void setBooks(List<Integer> books) {
        this.bookIds = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && login.equals(user.login) && password.equals(user.password)
                && name.equals(user.name) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, name, role);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
