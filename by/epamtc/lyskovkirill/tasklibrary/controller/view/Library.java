package by.epamtc.lyskovkirill.tasklibrary.controller.view;

import by.epamtc.lyskovkirill.tasklibrary.controller.Controller;

import java.util.Scanner;

public class Library {
    private final static Controller controller = new Controller();
    private final static Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("***Library! v1.0***");
        System.out.println("Type 'help' for command list");

        String request;
        String response;

        do {
            request = scanRequest();
            response = controller.executeTask(request);
            if (response != null)
                System.out.println(response);
            else break;
        } while (true);
    }

    private String scanRequest() {
        String request = null;
        try {
            request = scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return request;
    }
}