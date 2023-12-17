package guldilin.commands.update;

import guldilin.commands.common.EntitiesPrinter;
import guldilin.commands.common.Executor;
import guldilin.proxy.api.EntryNotFound_Exception;
import guldilin.service.ServiceProvider;

/**
 * Executor for update command. Update only specified fields.
 */
public class UpdateExecutor extends Executor<UpdateArgs> {
    /**
     * Create empty UpdateArgs instance for parsing later.
     *
     * @return empty args object for executor
     */
    @Override
    public UpdateArgs createEmptyArgs() {
        return new UpdateArgs();
    }

    /**
     * Execute update command.
     *
     * @param argv Unparsed CLI args
     * @param args Parsed CLI args
     * @param serviceProvider ServiceProvider implementation
     */
    @Override
    public void execute(final String[] argv, final UpdateArgs args, final ServiceProvider serviceProvider)
            throws EntryNotFound_Exception {
        var cityService = serviceProvider.provideCityService();
        var updated = cityService.patch(args.getId(), args.toDTO());
        EntitiesPrinter.print(System.out, updated);
    }
}
