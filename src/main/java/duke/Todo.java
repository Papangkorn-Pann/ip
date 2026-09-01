package duke;

public class Todo extends Task {
    public Todo(String name) {
        super(name);
    }

    public static Todo of(String info) throws DuckeException {
        if (info.isBlank()) {
            throw new DuckeException("The description of a todo cannot be empty");
        }
        return new Todo(info.trim());
    }

    @Override
    public String getTypeIcon() {
        return "T";
    }
}
