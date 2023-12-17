package guldilin.commands.common;

import com.beust.jcommander.JCommander;
import guldilin.service.ServiceProvider;
import guldilin.service.ServiceProviderImpl;

/**
 * Executor interface for commands.
 *
 * @param <T> Arguments class.
 */
public abstract class Executor<T extends Args> {

    /**
     * Create empty Args instance for parsing later. Should be implemented.
     *
     * @return empty args object for executor
     */
    public abstract T createEmptyArgs();

    /**
     * Execute command. Should be implemented.
     *
     * @param argv Unparsed CLI args
     * @param args Parsed CLI args
     * @param serviceProvider ServiceProvider implementation
     * @throws Exception if execution fails
     */
    public abstract void execute(String[] argv, T args, ServiceProvider serviceProvider) throws Exception;

    /**
     * Main execute function that should be invoked by CLI wrapper.
     *
     * @param argv Unparsed CLI args
     */
    public void executeWrapper(final String[] argv) {
        var args = this.parseArgs(argv);
        if (this.printOptionalHelp(args)) return;

        try {
            ServiceProvider serviceProvider = new ServiceProviderImpl(args.getUrl());
            this.execute(argv, args, serviceProvider);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Parse arguments object from CLI args.
     *
     * @param argv Unparsed CLI args
     * @return parsed Args object
     */
    public T parseArgs(final String[] argv) {
        var args = this.createEmptyArgs();
        JCommander.newBuilder().addObject(args).build().parse(argv);
        return args;
    }

    /**
     * Build JCommander arguments parser.
     *
     * @return JCommander object
     */
    public JCommander buildCommander() {
        return JCommander.newBuilder()
                .programName("lab2-client.jar")
                .addObject(this.createEmptyArgs())
                .build();
    }

    /**
     * Print help message if help argument is set.
     *
     * @param args Parsed CLI args
     * @return true if help provided
     */
    boolean printOptionalHelp(final Args args) {
        if (args.getHelp()) {
            buildCommander().usage();
            return true;
        }
        return false;
    }
}
