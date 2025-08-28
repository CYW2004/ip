package xiaobai;

public class ByeCommand extends Command {
    @Override
    public boolean isExit() { return true; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printBoxed("(¦3[▓▓] Bye! Hope to see you again soon!");
    }
}
