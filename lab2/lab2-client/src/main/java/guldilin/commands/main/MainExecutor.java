package guldilin.commands.main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import guldilin.commands.common.Args;
import guldilin.commands.common.Executor;
import guldilin.commands.common.HelpArgs;
import guldilin.commands.create.CreateExecutor;
import guldilin.commands.delete.DeleteExecutor;
import guldilin.commands.find.FindExecutor;
import guldilin.commands.patch.PatchExecutor;
import guldilin.commands.update.UpdateExecutor;
import guldilin.service.ServiceProvider;

/**
 * Executor for main method.
 */
public class MainExecutor extends Executor<MainArgs> {
    /**
     * Get executor by passed command.
     *
     * @param args Parsed arguments
     * @return Executor object
     */
    private Executor<?> getExecutorByCommand(final Args args) {
        return switch (args.getCommand()) {
            case find -> new FindExecutor();
            case create -> new CreateExecutor();
            case update -> new UpdateExecutor();
            case patch -> new PatchExecutor();
            case delete -> new DeleteExecutor();
            default -> throw new ParameterException("Executor for command not found");
        };
    }

    /**
     * Main execute function that should be invoked by CLI wrapper.
     *
     * @param argv Unparsed CLI args
     */
    @Override
    public void executeWrapper(final String[] argv) {
        HelpArgs argsHelp = new HelpArgs();
        JCommander.newBuilder().addObject(argsHelp).build().parse(argv);
        if (argsHelp.getHelp() && argsHelp.getCommand() == null) {
            buildCommander().usage();
            return;
        }

        try {
            var args = this.parseArgs(argv);
            this.execute(argv, args, null);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Create empty MainArgs instance for parsing later.
     *
     * @return empty args object for executor
     */
    @Override
    public MainArgs createEmptyArgs() {
        return new MainArgs();
    }

    /**
     * Execute main command.
     *
     * @param argv            Unparsed CLI args
     * @param args            Parsed CLI args
     * @param serviceProvider ServiceProvider implementation
     */
    @Override
    public void execute(final String[] argv, final MainArgs args, final ServiceProvider serviceProvider) {
        Executor<?> executor = getExecutorByCommand(args);
        executor.executeWrapper(argv);
    }
}
