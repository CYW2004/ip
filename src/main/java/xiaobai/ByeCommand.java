package xiaobai;

public class ByeCommand extends Command {
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * Prints a goodbye message to the user.
     *
     * @param tasks Task list.
     * @param ui User interface.
     * @param storage Storage handler.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        assert ui != null : "Ui must not be null";
        ui.printBoxed("(¦3[▓▓] Bye! Hope to see you again soon!");
    }
}
