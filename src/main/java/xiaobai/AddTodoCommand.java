package xiaobai;

public class AddTodoCommand extends Command {
    private final String desc;

    /**
     * Creates an AddTodoCommand with the given task description.
     *
     * @param desc Description of task.
     */
    public AddTodoCommand(String desc) {
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
        if (desc == null || desc.isBlank()) {
            throw new XiaoBaiException("The description of a todo cannot be empty.");
        }
        Task t = new Todo(desc.trim());
        tasks.add(t);
        ui.printBoxed("Got it. I've added this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
        save(storage, tasks, ui);
    }
}
