package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    private final LocalDate start;
    private final LocalDate end;

    public Event(String name, String start, String end) throws DuckeException {
        super(name);
        try {
            this.start = LocalDate.parse(start.trim());
            this.end = LocalDate.parse(end.trim());
        } catch (DateTimeParseException e) {
            throw new DuckeException("Please use dates like 2019-10-15.");
        }
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String getExtraInfo() {
        return " (from: " + start.format(DISPLAY_FORMAT) + " to: " + end.format(DISPLAY_FORMAT) + ")";
    }

    @Override
    public String toSaveFormat() {
        return super.toSaveFormat() + " | " + start + " | " + end;
    }
}
