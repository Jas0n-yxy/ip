package xuan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Deadline task with due date
 */
public class Deadline extends Task {
    protected LocalDate byDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter
            .ofPattern("MMM dd yyyy")
            .withLocale(Locale.ENGLISH);

    public Deadline(String description, String by) throws LoxyException {
        super(description);
        try {
            this.byDate = LocalDate.parse(by.trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new LoxyException("Invalid date format! Use yyyy-MM-dd");
        }
    }

    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + byDate.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDate.format(OUTPUT_FORMAT) + ")";
    }

    public LocalDate getByDate() {
        return byDate;
    }
}