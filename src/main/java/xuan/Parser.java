package xuan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parse user commands
 */
public class Parser {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Command parse(String input) throws LoxyException {
        if (input.isEmpty()) {
            throw new LoxyException("Please enter a command!");
        }

        String[] parts = input.split(" ", 2);
        String cmd = parts[0].toLowerCase();

        return switch (cmd) {
            case "bye" -> new ExitCommand();
            case "list" -> new ListCommand();
            case "mark" -> handleMarkCommand(parts, true);
            case "unmark" -> handleMarkCommand(parts, false);
            case "todo" -> handleTodoCommand(parts);
            case "deadline" -> handleDeadlineCommand(parts);
            case "event" -> handleEventCommand(parts);
            case "delete" -> handleDeleteCommand(parts);
            case "find" -> createFindCommand(parts);
            default -> throw new LoxyException("Unknown command!");
        };
    }

    private static Command handleMarkCommand(String[] parts, boolean isDone) throws LoxyException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new LoxyException("Please specify a task number after '" + parts[0] + "'.");
        }
        try {
            int taskNumber = Integer.parseInt(parts[1]);
            return new MarkCommand(taskNumber - 1, isDone);
        } catch (NumberFormatException e) {
            throw new LoxyException("Please enter a valid number after '" + parts[0] + "'.");
        }
    }

    private static Command handleTodoCommand(String[] parts) throws LoxyException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new LoxyException("The description of a todo cannot be empty.");
        }
        return new AddCommand(new Todo(parts[1].trim()));
    }

    private static Command handleDeadlineCommand(String[] parts) throws LoxyException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new LoxyException("The description of a deadline cannot be empty.");
        }
        String[] deadlineParts = parts[1].split(" /by ", 2);
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
            throw new LoxyException("Please use format: deadline [content] /by [yyyy-MM-dd]");
        }
        return new AddCommand(new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim()));
    }

    private static Command handleEventCommand(String[] parts) throws LoxyException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new LoxyException("The description of an event cannot be empty.");
        }
        String[] eventParts = parts[1].split(" /from ", 2);
        if (eventParts.length < 2 || eventParts[0].trim().isEmpty()) {
            throw new LoxyException("Please use format: event [content] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
        }
        String[] timeParts = eventParts[1].split(" /to ", 2);
        if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            throw new LoxyException("Please specify both start and end date (yyyy-MM-dd) for event.");
        }
        return new AddCommand(new Event(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim()));
    }

    private static Command handleDeleteCommand(String[] parts) throws LoxyException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new LoxyException("Please specify a task number after 'delete'.");
        }
        try {
            int taskNumber = Integer.parseInt(parts[1]);
            return new DeleteCommand(taskNumber - 1);
        } catch (NumberFormatException e) {
            throw new LoxyException("Please enter a valid number after 'delete'.");
        }
    }

    private static Command createFindCommand(String[] parts) throws LoxyException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new LoxyException("Please specify a keyword or date (yyyy-MM-dd) after 'find'.");
        }
        String searchInput = parts[1].trim();

        try {
            LocalDate.parse(searchInput, DATE_FORMAT);
            return new FindByDateCommand(searchInput);
        } catch (DateTimeParseException e) {
            return new FindByKeywordCommand(searchInput);
        }
    }
}