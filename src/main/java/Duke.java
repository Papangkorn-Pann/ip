import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public class Duke {
    public static void main(String[] args) {
        Banner.print();
        System.out.println("Hi, I am Ducke! Your quackbot!");
        System.out.println("Waddle you want me to do?");
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("data/ducke.txt");
        ArrayList<Task> tasks;
        try {
            tasks = storage.load();
        } catch (IOException | DuckeException e) {
            System.out.println("No previously saved tasks.");
            tasks = new ArrayList<>();
        }

        boolean isRunning = true;
        while (isRunning) {
            String[] input = scanner.nextLine().split(" ", 2);
            String keyword = input[0].toLowerCase();
            String argument = input.length > 1 ? input[1] : "";

            try {
                Command command = Command.valueOf(keyword.toUpperCase());  // "mark" → Command.MARK

                switch (command) {
                    case BYE -> isRunning = false;
                    case LIST -> printList(tasks);
                    case MARK -> markTask(tasks, argument);
                    case UNMARK -> unmarkTask(tasks, argument);
                    case TODO -> addTodo(tasks, argument);
                    case DEADLINE -> addDeadline(tasks, argument);
                    case EVENT -> addEvent(tasks, argument);
                    case DELETE -> deleteTask(tasks, argument);
                    default -> System.out.println("Quack?");
                }
                storage.save(tasks);
            } catch (IllegalArgumentException e) {
                System.out.println("I don't understand your quack command.");
            } catch (DuckeException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {                       // ← save() can throw this
                System.out.println("Couldn't save your tasks: " + e.getMessage());
            }

        }
        System.out.println("Quack quack! (bye bye)");
    }

    private static void printTaskCount(ArrayList<Task> tasks) {
        System.out.println("Now you have " + tasks.size()
                + (tasks.size() == 1 ? " task" : " tasks") + " in the list.");
    }

    private static void printList(ArrayList<Task> tasks) {
        if (!tasks.isEmpty()) {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        } else {
            System.out.println("No tasks. Life is ponderful");
        }
    }

    private static void markTask(ArrayList<Task> tasks, String indexStr) throws DuckeException
    {
        if (indexStr.isBlank()) {
            throw new DuckeException("Quack! Which task should I mark? (e.g. mark 2)");
        }
        try {
            int index = Integer.parseInt(indexStr);
            Task task = tasks.get(index - 1);
            System.out.println("Quack! I've marked this task as done:");
            task.markDone();
            System.out.println(task);
        } catch (NumberFormatException e) {
            throw new DuckeException("Quack! '" + indexStr + "' isn't a number. Try: mark 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DuckeException("Quack! There's no task number " + indexStr + ".");
        }
    }

    private static void unmarkTask(ArrayList<Task> tasks, String indexStr) throws DuckeException
    {
        if (indexStr.isBlank()) {
            throw new DuckeException("Quack! Which task should I unmark? (e.g. unmark 2)");
        }
        try {
            int index = Integer.parseInt(indexStr);
            Task task = tasks.get(index - 1);
            System.out.println("Quack! I've marked this task as not done yet");
            task.unmarkDone();
            System.out.println(task);
        } catch (NumberFormatException e) {
            throw new DuckeException("Quack! '" + indexStr + "' isn't a number. Try: unmark 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DuckeException("Quack! There's no task number " + indexStr + ".");
        }
    }


    private static void addTodo(ArrayList<Task> tasks, String info) throws DuckeException
    {
        if (info.isBlank()) {
            throw new DuckeException("The description of a todo cannot be empty");
        }
        Task task = new Todo(info);
        tasks.add(task);
        System.out.println("Added: ");
        System.out.println(task);
        printTaskCount(tasks);
    }

    private static void addDeadline(ArrayList<Task> tasks, String info) throws DuckeException
    {
        // Split on "/by" (no surrounding spaces required) so an empty
        // description like "/by Sunday" is still separated correctly
        // and would trigger correct ducke exception call
        String[] temp = info.split("/by", 2);
        String description = temp[0].trim();

        if (description.isBlank()) {
            throw new DuckeException("Task description cannot be empty.");
        }
        if (temp.length < 2 || temp[1].isBlank()) {
            throw new DuckeException("A deadline needs a /by time. e.g. deadline return book /by Sunday");
        }

        Task task = new Deadline(description, temp[1].trim());
        tasks.add(task);
        System.out.println("Added: ");
        System.out.println(task);
        printTaskCount(tasks);
    }

    private static void addEvent(ArrayList<Task> tasks, String info) throws DuckeException
    {
        // Split off the description first, then split the remainder into /from and /to.
        String[] a = info.split("/from", 2);
        String description = a[0].trim();

        if (description.isBlank()) {
            throw new DuckeException("Task description cannot be empty.");
        }
        if (a.length < 2) {
            throw new DuckeException("An event needs /from and /to times. e.g. event meeting /from Mon /to Tue");
        }

        String[] b = a[1].split("/to", 2);
        if (b.length < 2 || b[0].isBlank() || b[1].isBlank()) {
            throw new DuckeException("An event needs /from and /to times. e.g. event meeting /from Mon /to Tue");
        }

        Task task = new Event(description, b[0].trim(), b[1].trim());
        tasks.add(task);
        System.out.println("Added: ");
        System.out.println(task);
        printTaskCount(tasks);
    }

    private static void deleteTask(ArrayList<Task> tasks, String indexStr) throws DuckeException
    {
        if (indexStr.isBlank()) {
            throw new DuckeException("Which task should I delete? (e.g. delete 2)");
        }
        try {
            int index = Integer.parseInt(indexStr);
            Task removed = tasks.remove(index - 1);   // remove returns the removed Task
            System.out.println("Removed: ");
            System.out.println(removed);
            printTaskCount(tasks);
        } catch (NumberFormatException e) {
            throw new DuckeException("Please give a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            throw new DuckeException("Quack! There's no task number " + indexStr + ".");
        }
    }
}
