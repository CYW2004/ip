import java.util.Scanner;

public class XiaoBai {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100];
        boolean[] isDone = new boolean[100];
        int taskCount = 0;

        String logo_text = "__  __ ___    _    ___   ____    _    ___ \n"
                        + "\\ \\/ /|_ _|  / \\  / _ \\ | __ )  / \\  |_ _|\n"
                        + " \\  /  | |  / _ \\| | | ||  _ \\ / _ \\  | | \n"
                        + " /  \\  | | / ___ \\ |_| || |_) / ___ \\ | | \n"
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
                    String status = isDone[i] ? "[X]" : "[ ]";
                    System.out.println(" " + (i + 1) + "." + status + " " + tasks[i]);
                }
                System.out.println("____________________________________________________________");

            } else if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (index >= 0 && index < taskCount) {
                        isDone[index] = true;
                        System.out.println("____________________________________________________________");
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   [X] " + tasks[index]);
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
                        isDone[index] = false;
                        System.out.println("____________________________________________________________");
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   [ ] " + tasks[index]);
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

            } else {
                if (taskCount < 100) {
                    tasks[taskCount] = input;
                    isDone[taskCount] = false;
                    taskCount++;
                    System.out.println("____________________________________________________________");
                    System.out.println(" added: " + input);
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Sorry, task list is full.");
                    System.out.println("____________________________________________________________");
                }
            }
        }

        scanner.close();
    }
}
