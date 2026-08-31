package duke;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showWelcome() {
        Banner.print();
        System.out.println("Hi, I am Ducke! Your quackbot!");
        System.out.println("Waddle you want me to do?");
    }

    public void showGoodbye() {
        System.out.println("Quack quack! (bye bye)");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void show(String message) {
        System.out.println(message);
    }
}
