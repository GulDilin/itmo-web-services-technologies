package guldilin.commands.patch;

import guldilin.commands.common.EntitiesPrinter;
import guldilin.commands.common.Executor;
import guldilin.proxy.api.EntryNotFound_Exception;
import guldilin.service.ServiceProvider;

public class PatchExecutor extends Executor<PatchArgs> {
    /**
     * Create empty UpdateArgs instance for parsing later.
     *
     * @return empty args object for executor
     */
    @Override
    public PatchArgs createEmptyArgs() {
        return new PatchArgs();
    }

    /**
     * Execute patch command.
     *
     * @param argv Unparsed CLI args
     * @param args Parsed CLI args
     * @param serviceProvider ServiceProvider implementation
     */
    @Override
    public void execute(final String[] argv, final PatchArgs args, final ServiceProvider serviceProvider)
            throws EntryNotFound_Exception {
        var cityService = serviceProvider.provideCityService();
        var updated = cityService.patch(args.getId(), args.toDTO());
        EntitiesPrinter.print(System.out, updated);
    }
}
