package xuan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDate fromDate;
    protected LocalDate toDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Event(String description, String from, String to) throws LoxyException {
        super(description);
        try {
            this.fromDate = LocalDate.parse(from.trim(), INPUT_FORMAT);
            this.toDate = LocalDate.parse(to.trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new LoxyException("Invalid date format! Please use yyyy-MM-dd (e.g., 2019-12-02)");
        }
    }

    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | " + fromDate.format(INPUT_FORMAT) + " | " + toDate.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDate.format(OUTPUT_FORMAT) + " to: " + toDate.format(OUTPUT_FORMAT) + ")";
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }
}