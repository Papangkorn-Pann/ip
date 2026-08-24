import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        Banner.print();
        System.out.println("Hi, I am Ducke! Your quackbot!");
        System.out.println("Waddle you want me to do?");
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        boolean isRunning = true;
        while (isRunning) {
            //i hope singleton words still pass
            String[] input = (scanner.nextLine()).split(" ", 2);
            String keyword = input[0].toLowerCase();

            switch (keyword) {
                case "bye" -> isRunning = false;
                case "list" -> printList(tasks);
                case "mark" -> markTask(tasks, input[1]);
                case "unmark" -> unmarkTask(tasks, input[1]);
                case "todo" -> addTodo(tasks, input[1]);
                case "deadline" -> addDeadline(tasks, input[1]);
                case "event" -> addEvent(tasks, input[1]);

                default -> System.out.println("Quack?");
            }
        }
        System.out.println("Quack quack! (bye bye)");
    }

    private static void printList(ArrayList<Task> tasks) {
        if (!tasks.isEmpty()) {
            for (Task task : tasks) {
                task.printTask();
            }
        } else {
            System.out.println("No tasks. Life is ponderful");
        }
    }

    private static void markTask(ArrayList<Task> tasks, String indexStr) {
        int index = Integer.parseInt(indexStr);
        Task task = tasks.get(index - 1);
        System.out.println("Quack! I've marked this task as done:");
        task.markDone();
        task.printTask();
    }

    private static void unmarkTask(ArrayList<Task> tasks, String indexStr) {
        int index = Integer.parseInt(indexStr);
        Task task = tasks.get(index - 1);
        System.out.println("Quack! I've marked this task as not done yet");
        task.unmarkDone();
        task.printTask();
    }

    private static void addTodo(ArrayList<Task> tasks, String info) {
        Task task = new Todo(info);
        tasks.add(task);
        System.out.println("Added: ");
        task.printTask();
    }

    private static void addDeadline(ArrayList<Task> tasks, String info) {
        String[] temp = info.split(" /by ");
        Task task = new Deadline(temp[0], temp[1]);
        tasks.add(task);
        System.out.println("Added: ");
        task.printTask();
    }

    private static void addEvent(ArrayList<Task> tasks, String info) {
        String[] a = info.split(" /from ");       // ["meeting", "Mon /to Tue"]
        String[] b = a[1].split(" /to ");
        Task task = new Event(a[0], b[0], b[1]);
        tasks.add(task);
        System.out.println("Added: ");
        task.printTask();
    }

}
