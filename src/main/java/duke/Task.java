package duke;

public class Task {

    private String name;
    private boolean done;

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    public String getName() {
        return this.name;
    }

    public boolean isDone() {
        return this.done;
    }

    public String getTypeIcon() {
        return " ";
    }

    public String getStatusIcon() {
        return (this.done ? "X" : " ");
    }

    public void markDone() {
        this.done = true;
    }

    public void unmarkDone() {
        this.done = false;
    }

    public String getExtraInfo() {
        return "";
    }

    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + getName() + getExtraInfo();
    }

    public String toSaveFormat() {
        return getTypeIcon() + " | " + (isDone() ? "1" : "0") + " | " + getName();
    }
}
