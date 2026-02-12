import java.util.Scanner;

public class Loxy {
    private static final String SEPARATOR = "____________________________________________________________";

    public static void main(String[] args) {
        printWelcomeLogo();
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userInput = scanner.nextLine().trim();
            if ("bye".equalsIgnoreCase(userInput)) {
                break;
            }
            echoUserInput(userInput);
        }

        printGoodbyeMessage();
        scanner.close();
    }

    private static void printWelcomeLogo() {
        String logo =
                " | |     / __ \\\\ \\\\  // \\\\  //\n"
                        + " | |    | |  | |  \\\\//   \\\\// \n"
                        + " | |___ | |__| |  //\\\\    ||  \n"
                        + " |______|\\\\___/  //  \\\\   ||  \n";
        System.out.println("Hello from\n" + logo);
    }

    private static void printWelcomeMessage() {
        System.out.println(SEPARATOR);
        System.out.println(" Hello! I'm Loxy");
        System.out.println(" What can I do for you?");
        System.out.println(SEPARATOR);
    }

    private static void echoUserInput(String userInput) {
        System.out.println(SEPARATOR);
        System.out.println(" " + userInput);
        System.out.println(SEPARATOR);
    }

    private static void printGoodbyeMessage() {
        System.out.println(SEPARATOR);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }
}