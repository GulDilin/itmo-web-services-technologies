package guldilin.commands.update;

import guldilin.commands.common.EntitiesPrinter;
import guldilin.commands.common.Executor;
import guldilin.service.ServiceProvider;
import lombok.SneakyThrows;

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
    @SneakyThrows
    public void execute(final String[] argv, final UpdateArgs args, final ServiceProvider serviceProvider) {
        var cityService = serviceProvider.provideCityService();
        var updated = cityService.patch(args.getId(), args.toDTO());
        EntitiesPrinter.print(System.out, updated);
    }
}
