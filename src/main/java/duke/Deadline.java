package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    private final LocalDate date;

    public Deadline(String name, String date) throws DuckeException {
        super(name);
        try {
            this.date = LocalDate.parse(date.trim());
        } catch (DateTimeParseException e) {
            throw new DuckeException("Please use a date like 2019-10-15.");
        }
    }

    public static Deadline of(String info) throws DuckeException {
        String[] temp = info.split("/by", 2);
        String description = temp[0].trim();

        if (description.isBlank()) {
            throw new DuckeException("Task description cannot be empty.");
        }
        if (temp.length < 2 || temp[1].isBlank()) {
            throw new DuckeException("A deadline needs a /by time. e.g. deadline return book /by Sunday");
        }

        return new Deadline(description, temp[1].trim());
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String getExtraInfo() {
        return " (by: " + date.format(DISPLAY_FORMAT) + ")";
    }

    @Override
    public String toSaveFormat() {
        return super.toSaveFormat() + " | " + date;
    }
}
