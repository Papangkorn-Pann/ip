package duke;

import java.io.IOException;

public class Duke {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException | DuckeException e) {
            ui.showError("No previously saved tasks.");
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isRunning = true;
        while (isRunning) {
            String fullCommand = ui.readCommand();

            try {
                Command command = Parser.parseCommand(fullCommand);
                String argument = Parser.parseArgument(fullCommand);

                switch (command) {
                    case BYE -> isRunning = false;
                    case LIST -> printList();
                    case MARK -> markTask(argument);
                    case UNMARK -> unmarkTask(argument);
                    case TODO -> addTodo(argument);
                    case DEADLINE -> addDeadline(argument);
                    case EVENT -> addEvent(argument);
                    case DELETE -> deleteTask(argument);
                    default -> ui.show("Quack?");
                }
                storage.save(tasks);
            } catch (DuckeException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Couldn't save your tasks: " + e.getMessage());
            }
        }
        ui.showGoodbye();
    }

    public static void main(String[] args) {
        new Duke("data/ducke.txt").run();
    }

    private void printTaskCount() {
        ui.show("Now you have " + tasks.size()
                + (tasks.size() == 1 ? " task" : " tasks") + " in the list.");
    }

    private void printList() {
        if (!tasks.isEmpty()) {
            for (int i = 0; i < tasks.size(); i++) {
                ui.show((i + 1) + ". " + tasks.get(i));
            }
        } else {
            ui.show("No tasks. Life is ponderful");
        }
    }

    private void markTask(String indexStr) throws DuckeException {
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

    private void unmarkTask(String indexStr) throws DuckeException {
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

    private void addTodo(String info) throws DuckeException {
        Task task = Todo.of(info);
        tasks.add(task);
        ui.show("Added: ");
        ui.show(task.toString());
        printTaskCount();
    }

    private void addDeadline(String info) throws DuckeException {
        Task task = Deadline.of(info);
        tasks.add(task);
        ui.show("Added: ");
        ui.show(task.toString());
        printTaskCount();
    }

    private void addEvent(String info) throws DuckeException {
        Task task = Event.of(info);
        tasks.add(task);
        ui.show("Added: ");
        ui.show(task.toString());
        printTaskCount();
    }

    private void deleteTask(String indexStr) throws DuckeException {
        if (indexStr.isBlank()) {
            throw new DuckeException("Which task should I delete? (e.g. delete 2)");
        }
        try {
            int index = Integer.parseInt(indexStr);
            Task removed = tasks.delete(index - 1);
            ui.show("Removed: ");
            ui.show(removed.toString());
            printTaskCount();
        } catch (NumberFormatException e) {
            throw new DuckeException("Please give a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            throw new DuckeException("Quack! There's no task number " + indexStr + ".");
        }
    }
}
