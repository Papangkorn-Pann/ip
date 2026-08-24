import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        Banner.print();
        System.out.println("Hi, I am Ducke! Your quackbot!");
        System.out.println("Waddle you want me to do?");
        Scanner scanner = new Scanner(System.in);
        String command = "";
        ArrayList<String> tasks = new ArrayList<>();
        //tasks.add(newTask);
        //tasks.size();
        while (!command.equals("bye")) {
            command = scanner.nextLine();

            if (command.equals("list")) {
                if (!tasks.isEmpty()) {
                    int counter = 1;
                    for (String task : tasks) {
                        System.out.print(counter);
                        System.out.print(". ");
                        System.out.println(task);
                        counter++;
                    }
                } else {
                    System.out.println("No tasks. Life is ponderful");
                }
            } else {
                tasks.add(command);
                System.out.print("Added: ");
                System.out.println(command);
            }
        }
        System.out.println("Quack quack! (bye bye)");
    }
}
