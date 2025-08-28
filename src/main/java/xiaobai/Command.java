package xiaobai;

public abstract class Command {
    public boolean isExit() { return false; }
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException;

    protected void save(Storage storage, TaskList tasks, Ui ui) {
        if (storage != null) storage.save(tasks.asList(), ui);
    }
}

