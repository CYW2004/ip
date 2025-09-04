package xiaobai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() throws XiaoBaiException {
        taskList = new TaskList();
        taskList.add(new Todo("Read book"));
        taskList.add(new Deadline("Submit report", "2025-09-01 18:00"));
        taskList.add(new Event("Meeting", "2025-09-02 14:00", "2025-09-02 15:00"));
    }

    @Test
    public void testDeleteTaskValidIndex() throws Exception {
        Task removed = taskList.remove(1); // 0-based index assumed
        assertEquals("[T][ ] Read book", removed.toString(), "Deleted task should match");
        assertEquals(2, taskList.size(), "Size should decrease after deletion");
    }

    @Test
    public void testDeleteTaskMiddleElement() throws Exception {
        Task removed = taskList.remove(2);
        assertEquals("[D][ ] Submit report (by: Sep 1 2025 6:00PM)", removed.toString());
        assertEquals(2, taskList.size());
    }

    @Test
    public void testDeleteTaskInvalidIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.remove(10); // invalid index
        }, "Expected exception for invalid index");
    }

    @Test
    public void testDeleteTaskLastElement() throws Exception {
        Task removed = taskList.remove(3); // delete last element
        assertEquals("[E][ ] Meeting (from: Sep 2 2025 2:00PM to: Sep 2 2025 3:00PM)", removed.toString());
        assertEquals(2, taskList.size());
    }
}
