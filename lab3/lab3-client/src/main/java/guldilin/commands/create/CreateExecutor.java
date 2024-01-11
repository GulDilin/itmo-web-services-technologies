package guldilin.commands.create;

import guldilin.commands.common.EntitiesPrinter;
import guldilin.commands.common.Executor;
import guldilin.service.ServiceProvider;
import lombok.SneakyThrows;

/**
 * Executor for create method.
 */
public class CreateExecutor extends Executor<CreateArgs> {

    /**
     * Create empty CreateArgs instance for parsing later.
     *
     * @return empty args object for executor
     */
    @Override
    public CreateArgs createEmptyArgs() {
        return new CreateArgs();
    }

    /**
     * Execute create command.
     *
     * @param argv Unparsed CLI args
     * @param args Parsed CLI args
     * @param serviceProvider ServiceProvider implementation
     */
    @Override
    @SneakyThrows
    public void execute(final String[] argv, final CreateArgs args, final ServiceProvider serviceProvider) {
        var cityService = serviceProvider.provideCityService();
        var created = cityService.create(args.toDTO());
        EntitiesPrinter.print(System.out, created);
    }
}
