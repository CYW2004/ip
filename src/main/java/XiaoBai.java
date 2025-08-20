import java.util.Scanner;

public class XiaoBai {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        String logo_text = "__  __ ___    _    ___   ____    _    ___\n"
                        + "\\ \\/ /|_ _|  / \\  / _ \\ | __ )  / \\  |_ _|\n"
                        + " \\  /  | |  / _ \\| | | ||  _ \\ / _ \\  | |\n"
                        + " /  \\  | | / ___ \\ |_| || |_) / ___ \\ | |\n"
                        + "/_/\\_\\|___/_/   \\_\\___/ |____/_/   \\_\\___|\n";
        String expression = "(*^_^*)";


        System.out.println("____________________________________________________________");
        System.out.println(logo_text);
        System.out.println(expression);
        System.out.println(" Hello! I'm XiaoBai, your dragon assistant.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;

            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
                System.out.println("____________________________________________________________");

            } else if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[index]);
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("____________________________________________________________");
                        System.out.println(" Invalid task number.");
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Invalid format. Use: mark [task number]");
                    System.out.println("____________________________________________________________");
                }

            } else if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsNotDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[index]);
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("____________________________________________________________");
                        System.out.println(" Invalid task number.");
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Invalid format. Use: unmark [task number]");
                    System.out.println("____________________________________________________________");
                }

            } else if (input.startsWith("todo ")) {
                String description = input.substring(5).trim();
                Task t = new Todo(description);
                tasks[taskCount++] = t;
                System.out.println("____________________________________________________________");
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + t);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println("____________________________________________________________");

            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split("/by", 2);
                if (parts.length == 2) {
                    String desc = parts[0].trim();
                    String by = parts[1].trim();
                    Task d = new Deadline(desc, by);
                    tasks[taskCount++] = d;
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + d);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }

            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split("/from", 2);
                if (parts.length == 2) {
                    String desc = parts[0].trim();
                    String[] times = parts[1].split("/to", 2);
                    if (times.length == 2) {
                        String from = times[0].trim();
                        String to = times[1].trim();
                        Task e = new Event(desc, from, to);
                        tasks[taskCount++] = e;
                        System.out.println("____________________________________________________________");
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + e);
                        System.out.println(" Now you have " + taskCount + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    }
                }

            } else {
                Task t = new Todo(input);
                tasks[taskCount++] = t;
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + t);
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}
