import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Banner.print();
        System.out.println("Hi, I am Ducke! Your quackbot!");
        System.out.println("Waddle you want to do?");
        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (!command.equals("bye")) {
            command = scanner.nextLine();
            System.out.println(command);
        }
        System.out.println("Quack quack! (bye bye)");
    }
}
