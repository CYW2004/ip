package xiaobai;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        assert keyword != null : "Keyword must not be null";
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        assert tasks != null : "TaskList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new InvalidFormatException("Use: find <keyword>");
        }
        String needle = keyword.trim().toLowerCase();
        assert needle != null && !needle.isEmpty() : "Search needle must not be null or empty";

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        int n = 0;
        for (Task t : tasks.asList()) {
            assert t != null : "Task in list must not be null";
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
