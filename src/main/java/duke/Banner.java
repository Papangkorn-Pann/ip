package duke;

/**
 * Displays the application's ASCII-art startup banner.
 */
public class Banner {
    /**
     * Prints the ASCII-art banner to standard output.
     */
    public static void print() {
        String banner = " ____              _        \n"
                + "|  _ \\ _   _  ____| | _____ \n"
                + "| | | | | | |/ ___| |/ / _ \\\n"
                + "| |_| | |_| | |___    <  __/\n"
                + "|____/ \\__,_|\\____|__\\_\\___|\n";
        System.out.println(banner);
    }
}
