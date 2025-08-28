package xiaobai;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new InvalidFormatException("Use: find <keyword>");
        }
        String needle = keyword.trim().toLowerCase();

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        int n = 0;
        for (Task t : tasks.asList()) {
            if (t.description != null && t.description.toLowerCase().contains(needle)) {
                sb.append(" ").append(++n).append(".").append(t).append("\n");
            }
        }

        if (n == 0) {
            ui.printBoxed("No matching tasks found.");
        } else {
            ui.printBoxed(sb.toString().trim());
        }
    }
}
