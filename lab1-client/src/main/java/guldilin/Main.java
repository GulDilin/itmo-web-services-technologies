package guldilin;

import guldilin.commands.main.MainExecutor;

public final class Main {

    /**
     * Empty Constructor for Utility class.
     */
    private Main() {
        // empty
    }

    /**
     * Main.
     *
     * @param argv cli args
     */
    public static void main(final String[] argv) {
        try {
            new MainExecutor().execute(argv);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
