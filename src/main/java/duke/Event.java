package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final String FORMAT_ERROR =
            "An event needs /from and /to times. e.g. event meeting /from Mon /to Tue";

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

    public static Event of(String info) throws DuckeException {
        String[] a = info.split("/from", 2);
        String description = a[0].trim();

        if (description.isBlank()) {
            throw new DuckeException("Task description cannot be empty.");
        }
        if (a.length < 2) {
            throw new DuckeException(FORMAT_ERROR);
        }

        String[] b = a[1].split("/to", 2);
        if (b.length < 2 || b[0].isBlank() || b[1].isBlank()) {
            throw new DuckeException(FORMAT_ERROR);
        }

        return new Event(description, b[0].trim(), b[1].trim());
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
