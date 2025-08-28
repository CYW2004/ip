package xiaobai;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of tasks.
 * Supports operations: add, remove, mark, unmark, and render.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList with an initial list of tasks.
     * If the provided list is null, an empty list is used.
     *
     * @param initial Initial task list.
     */
    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial == null ? List.of() : initial);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given 1-based index.
     *
     * @param index1Based 1-based index of the task.
     * @return Task at the given index.
     */
    public Task get(int index1Based) {
        return tasks.get(index1Based - 1);
    }

    /**
     * Returns the internal list of tasks.
     *
     * @return List of tasks.
     */
    public List<Task> asList() {
        return tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param t Task to add.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Removes the task at the given index.
     *
     * @param index1Based 1-based index of the task
     * @return Removed task.
     */
    public Task remove(int index1Based) {
        return tasks.remove(index1Based - 1);
    }

    /**
     * Marks the task as done.
     *
     * @param index1Based 1-based index of the task.
     * @return Task that was marked as done.
     */
    public Task mark(int index1Based) {
        Task t = get(index1Based);
        t.markAsDone();
        return t;
    }

    /**
     * Marks the task as not done.
     *
     * @param index1Based 1-based index of the task.
     * @return Task that was marked as not done.
     */
    public Task unmark(int index1Based) {
        Task t = get(index1Based);
        t.markAsNotDone();
        return t;
    }

    /**
     * Returns a string representation of the task list,
     * including index numbers and task details.
     *
     * @return Formatted string of all tasks, or message if empty.
     */
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
