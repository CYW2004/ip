public class Parser {
    public static int parseIndexOrThrow(String raw, String usageHint) throws XiaoBaiException {
        try {
            int idx = Integer.parseInt(raw.trim()) - 1;
            return idx;
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Use: " + usageHint);
        }
    }

    public static void ensureIndexInRange(int index, int taskCount) throws XiaoBaiException {
        if (index < 0 || index >= taskCount) throw new InvalidIndexException();
    }
}
