package guldilin.commands.common;

public interface Executor {
    /**
     * Execute command.
     *
     * @param argv Unparsed CLI args
     * @throws Exception if execution fails
     */
    void execute(String[] argv) throws Exception;

    /**
     * Parse arguments object from CLI args.
     *
     * @param argv Unparsed CLI args
     * @return parsed Args object
     */
    Args parseArgs(String[] argv);
}
