package duke;

/**
 * Interprets raw user input into commands and arguments.
 */
public class Parser {

    /**
     * Parses the command keyword from a line of user input.
     *
     * @param input the full line typed by the user
     * @return the Command matching the first word
     * @throws DuckeException if the keyword is not a recognized command
     */
    public static Command parseCommand(String input) throws DuckeException {
        String keyword = input.split(" ", 2)[0].toUpperCase();
        try {
            return Command.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            throw new DuckeException("I don't understand your quack command.");
        }
    }

    /**
     * Extracts the argument portion of the input (everything after the keyword).
     *
     * @param input the full line typed by the user
     * @return the argument, or an empty string if there is none
     */
    public static String parseArgument(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}
