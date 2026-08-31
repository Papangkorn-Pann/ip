import java.io.IOException;

public class Duke {
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();
        Storage storage = new Storage("data/ducke.txt");
        TaskList tasks;
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException | DuckeException e) {
            ui.showError("No previously saved tasks.");
            tasks = new TaskList();
        }

        boolean isRunning = true;
        while (isRunning) {
            String[] input = ui.readCommand().split(" ", 2);
            String keyword = input[0].toLowerCase();
            String argument = input.length > 1 ? input[1] : "";

            try {
                Command command = Command.valueOf(keyword.toUpperCase());

                switch (command) {
                    case BYE -> isRunning = false;
                    case LIST -> printList(tasks, ui);
                    case MARK -> markTask(tasks, argument, ui);
                    case UNMARK -> unmarkTask(tasks, argument, ui);
                    case TODO -> addTodo(tasks, argument, ui);
                    case DEADLINE -> addDeadline(tasks, argument, ui);
                    case EVENT -> addEvent(tasks, argument, ui);
                    case DELETE -> deleteTask(tasks, argument, ui);
                    default -> ui.show("Quack?");
                }
                storage.save(tasks);
            } catch (IllegalArgumentException e) {
                ui.showError("I don't understand your quack command.");
            } catch (DuckeException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Couldn't save your tasks: " + e.getMessage());
            }

        }
        ui.showGoodbye();
    }

    private static void printTaskCount(TaskList tasks, Ui ui) {
        ui.show("Now you have " + tasks.size()
                + (tasks.size() == 1 ? " task" : " tasks") + " in the list.");
    }

    private static void printList(TaskList tasks, Ui ui) {
        if (!tasks.isEmpty()) {
            for (int i = 0; i < tasks.size(); i++) {
                ui.show((i + 1) + ". " + tasks.get(i));
            }
        } else {
            ui.show("No tasks. Life is ponderful");
        }
    }

    private static void markTask(TaskList tasks, String indexStr, Ui ui) throws DuckeException {
        if (indexStr.isBlank()) {
            throw new DuckeException("Quack! Which task should I mark? (e.g. mark 2)");
        }
        try {
            int index = Integer.parseInt(indexStr);
            Task task = tasks.get(index - 1);
            ui.show("Quack! I've marked this task as done:");
            task.markDone();
            ui.show(task.toString());
        } catch (NumberFormatException e) {
            throw new DuckeException("Quack! '" + indexStr + "' isn't a number. Try: mark 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DuckeException("Quack! There's no task number " + indexStr + ".");
        }
    }

    private static void unmarkTask(TaskList tasks, String indexStr, Ui ui) throws DuckeException {
        if (indexStr.isBlank()) {
            throw new DuckeException("Quack! Which task should I unmark? (e.g. unmark 2)");
        }
        try {
            int index = Integer.parseInt(indexStr);
            Task task = tasks.get(index - 1);
            ui.show("Quack! I've marked this task as not done yet");
            task.unmarkDone();
            ui.show(task.toString());
        } catch (NumberFormatException e) {
            throw new DuckeException("Quack! '" + indexStr + "' isn't a number. Try: unmark 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DuckeException("Quack! There's no task number " + indexStr + ".");
        }
    }

    private static void addTodo(TaskList tasks, String info, Ui ui) throws DuckeException {
        if (info.isBlank()) {
            throw new DuckeException("The description of a todo cannot be empty");
        }
        Task task = new Todo(info);
        tasks.add(task);
        ui.show("Added: ");
        ui.show(task.toString());
        printTaskCount(tasks, ui);
    }

    private static void addDeadline(TaskList tasks, String info, Ui ui) throws DuckeException {
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
        ui.show("Added: ");
        ui.show(task.toString());
        printTaskCount(tasks, ui);
    }

    private static void addEvent(TaskList tasks, String info, Ui ui) throws DuckeException {
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
        ui.show("Added: ");
        ui.show(task.toString());
        printTaskCount(tasks, ui);
    }

    private static void deleteTask(TaskList tasks, String indexStr, Ui ui) throws DuckeException {
        if (indexStr.isBlank()) {
            throw new DuckeException("Which task should I delete? (e.g. delete 2)");
        }
        try {
            int index = Integer.parseInt(indexStr);
            Task removed = tasks.delete(index - 1);
            ui.show("Removed: ");
            ui.show(removed.toString());
            printTaskCount(tasks, ui);
        } catch (NumberFormatException e) {
            throw new DuckeException("Please give a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            throw new DuckeException("Quack! There's no task number " + indexStr + ".");
        }
    }
}
