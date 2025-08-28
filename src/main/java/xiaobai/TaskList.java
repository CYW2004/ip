package xiaobai;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial == null ? List.of() : initial);
    }

    public int size() { return tasks.size(); }
    public Task get(int index1Based) { return tasks.get(index1Based - 1); }
    public List<Task> asList() { return tasks; }

    public void add(Task t) { tasks.add(t); }

    public Task remove(int index1Based) { return tasks.remove(index1Based - 1); }

    public Task mark(int index1Based) {
        Task t = get(index1Based);
        t.markAsDone();
        return t;
    }

    public Task unmark(int index1Based) {
        Task t = get(index1Based);
        t.markAsNotDone();
        return t;
    }

    public String renderList() {
        if (tasks.isEmpty()) {
            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}
