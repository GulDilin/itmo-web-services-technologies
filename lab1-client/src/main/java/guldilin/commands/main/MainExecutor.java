package guldilin.commands.main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import guldilin.commands.common.Args;
import guldilin.commands.common.Executor;
import guldilin.commands.find.FindExecutor;

/**
 * Executor for main method.
 */
public class MainExecutor implements Executor {
    /**
     * Get executor by passed command.
     *
     * @param args Parsed arguments
     * @return Executor object
     */
    private Executor getExecutorByCommand(final Args args) {
        return switch (args.getCommand()) {
            case find -> new FindExecutor();
            default -> throw new ParameterException("Executor for command not found");
        };
    }

    /**
     * Parse main arguments.
     *
     * @param argv Unparsed CLI args
     * @return Parsed arguments
     */
    @Override
    public MainArgs parseArgs(final String[] argv) {
        MainArgs args = new MainArgs();
        JCommander.newBuilder().addObject(args).build().parse(argv);

        return args;
    }

    /**
     * Execute main command.
     *
     * @param argv Unparsed CLI args
     * @throws Exception if execution failed
     */
    @Override
    public void execute(final String[] argv) throws Exception {
        MainArgs args = parseArgs(argv);
        Executor executor = getExecutorByCommand(args);
        executor.execute(argv);
    }
}
