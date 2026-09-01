package duke;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public TaskList find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (get(i).getName().contains(keyword)) {
                matches.add(get(i));
            }
        }
        return new TaskList(matches);
    }
}
