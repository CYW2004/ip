package xiaobai;

public class AddTodoCommand extends Command {
    private final String desc;

    /**
     * Creates an AddTodoCommand with the given task description.
     *
     * @param desc Description of task.
     */
    public AddTodoCommand(String desc) {
        assert desc != null : "Description must not be null";
        this.desc = desc;
    }

    /**
     * Creates a Todo task.
     * Saves the updated task list.
     *
     * @param tasks Task list.
     * @param ui User interface.
     * @param storage Storage handler.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        assert tasks != null : "TaskList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";
        if (desc == null || desc.isBlank()) {
            throw new XiaoBaiException("The description of a todo cannot be empty.");
        }
        int oldSize = tasks.size();
        Task t = new Todo(desc.trim());
        assert t != null : "Created task must not be null";
        tasks.add(t);
        assert tasks.size() == oldSize + 1 : "Task list size should increase after add";
        ui.printBoxed("Got it. I've added this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
        save(storage, tasks, ui);
        assert tasks.size() > 0 : "After save, tasks should still be present";
    }
}
