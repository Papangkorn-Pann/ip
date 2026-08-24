import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        Banner.print();
        System.out.println("Hi, I am Ducke! Your quackbot!");
        System.out.println("Waddle you want me to do?");
        Scanner scanner = new Scanner(System.in);
        String command = "";
        ArrayList<Task> tasks = new ArrayList<>();

        while (!command.equals("bye")) {
            command = scanner.nextLine();

            if (command.equals("list")) {
                if (!tasks.isEmpty()) {
                    for (Task task : tasks) {
                        task.printTask();
                    }
                } else {
                    System.out.println("No tasks. Life is ponderful");
                }
            } else if (command.startsWith("mark ")) {
                int index = Integer.parseInt(command.split(" ")[1]);
                Task task = tasks.get(index - 1);
                System.out.println("Quack! I've marked this task as done:");
                task.markDone();
                task.printTask();
            } else if (command.startsWith("unmark ")) {
                int index = Integer.parseInt(command.split(" ")[1]);
                Task task = tasks.get(index - 1);
                System.out.println("Quack! I've marked this task as not done yet");
                task.unmarkDone();
                task.printTask();
            } else {
                tasks.add(new Task(command));
                System.out.print("Added: ");
                System.out.println(command);
            }
        }
        System.out.println("Quack quack! (bye bye)");
    }
}
