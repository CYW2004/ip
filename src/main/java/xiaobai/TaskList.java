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
        assert tasks != null : "Tasks list must not be null after initialization";
        assert tasks.isEmpty() : "New TaskList must be empty";
    }

    /**
     * Creates a TaskList with an initial list of tasks.
     * If the provided list is null, an empty list is used.
     *
     * @param initial Initial task list.
     */
    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial == null ? List.of() : initial);
        assert tasks != null : "Tasks list must not be null after initialization";
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks.
     */
    public int size() {
        int size = tasks.size();
        assert size >= 0 : "Size must not be negative";
        return size;
    }

    /**
     * Returns the task at the given 1-based index.
     *
     * @param index1Based 1-based index of the task.
     * @return Task at the given index.
     */
    public Task get(int index1Based) {
        assert index1Based > 0 && index1Based <= tasks.size() : "Index out of bounds";
        Task t = tasks.get(index1Based - 1);
        assert t != null : "Retrieved task must not be null";
        return t;
    }

    /**
     * Returns the internal list of tasks.
     *
     * @return List of tasks.
     */
    public List<Task> asList() {
        assert tasks != null : "Tasks list must not be null";
        return tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param t Task to add.
     */
    public void add(Task t) {
        assert t != null : "Task to add must not be null";
        int oldSize = tasks.size();
        tasks.add(t);
        assert tasks.size() == oldSize + 1 : "Size must increase after add";
    }

    /**
     * Removes the task at the given index.
     *
     * @param index1Based 1-based index of the task
     * @return Removed task.
     */
    public Task remove(int index1Based) {
        assert index1Based > 0 && index1Based <= tasks.size() : "Index out of bounds for remove";
        int oldSize = tasks.size();
        Task t = tasks.remove(index1Based - 1);
        assert t != null : "Removed task must not be null";
        assert tasks.size() == oldSize - 1 : "Size must decrease after remove";
        return t;
    }

    /**
     * Marks the task as done.
     *
     * @param index1Based 1-based index of the task.
     * @return Task that was marked as done.
     */
    public Task mark(int index1Based) {
        Task t = get(index1Based);
        assert t != null : "Task to mark must not be null";
        t.markAsDone();
        assert t.isDone : "Task should be marked as done";
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
        assert t != null : "Task to unmark must not be null";
        t.markAsNotDone();
        assert !t.isDone : "Task should be marked as not done";
        return t;
    }

    /**
     * Returns a string representation of the task list,
     * including index numbers and task details.
     *
     * @return Formatted string of all tasks, or message if empty.
     */
    public String renderList() {
        assert tasks != null : "Tasks list must not be null";
        if (tasks.isEmpty()) {
            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            assert t != null : "Task in list must not be null";
            sb.append(" ").append(i + 1).append(".").append(t).append("\n");
        }
        String result = sb.toString().trim();
        assert result != null : "Rendered string must not be null";
        return result;
    }
}
