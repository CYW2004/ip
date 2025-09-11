package xiaobai;

public abstract class Command {
    public boolean isExit() {
        return false;
    }

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException;
    protected void save(Storage storage, TaskList tasks, Ui ui) {
        assert tasks != null : "TaskList must not be null";
        assert ui != null : "Ui must not be null";
        if (storage != null) {
            storage.save(tasks.asList(), ui);
        }
    }
}
