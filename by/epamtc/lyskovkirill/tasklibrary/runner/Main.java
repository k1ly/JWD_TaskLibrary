package by.epamtc.lyskovkirill.tasklibrary.runner;

import by.epamtc.lyskovkirill.tasklibrary.controller.view.Library;

public class Main {

    public static void main(String[] args) {
        try {
            Library Main = new Library();
            Main.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}