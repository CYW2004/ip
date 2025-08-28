package xiaobai;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testParseAddTodoCommand() throws Exception {
        Command command = Parser.parse("todo read book");
        assertTrue(command instanceof AddTodoCommand, "Expected AddTodoCommand");
    }

    @Test
    public void testParseAddDeadlineCommand() throws Exception {
        Command command = Parser.parse("deadline submit report /by 2025-09-01 18:00");
        assertTrue(command instanceof AddDeadlineCommand, "Expected AddDeadlineCommand");
    }

    @Test
    public void testParseAddEventCommand() throws Exception {
        Command command = Parser.parse("event project meeting /from 2025-09-02 14:00 /to 2025-09-02 16:00");
        assertTrue(command instanceof AddEventCommand, "Expected AddEventCommand");
    }

    @Test
    public void testParseByeCommand() throws Exception {
        Command command = Parser.parse("bye");
        assertTrue(command instanceof ByeCommand, "Expected ByeCommand");
    }

    @Test
    public void testParseInvalidCommandThrowsException() {
        assertThrows(XiaoBaiException.class, () -> {
            Parser.parse("nonsense command");
        }, "Expected XiaoBaiException for invalid input");
    }
}
