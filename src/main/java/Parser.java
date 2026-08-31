public class Parser {
    public static Command parseCommand(String input) throws DuckeException {
        String keyword = input.split(" ", 2)[0].toUpperCase();
        try {
            return Command.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            throw new DuckeException("I don't understand your quack command.");
        }
    }

    public static String parseArgument(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}
