package guldilin.commands.delete;

import guldilin.commands.common.Executor;
import guldilin.service.ServiceProvider;

/**
 * Executor for create method.
 */
public class DeleteExecutor extends Executor<DeleteArgs> {

    /**
     * Create empty Args instance for parsing later.
     *
     * @return empty args object for executor
     */
    @Override
    public DeleteArgs createEmptyArgs() {
        return new DeleteArgs();
    }

    /**
     * Execute delete command.
     *
     * @param argv Unparsed CLI args
     * @param args Parsed CLI args
     * @param serviceProvider ServiceProvider implementation
     */
    @Override
    public void execute(final String[] argv, final DeleteArgs args, final ServiceProvider serviceProvider) {
        var cityService = serviceProvider.provideCityService();
        var deleted = cityService.deleteById(args.getId());
        if (deleted) {
            System.out.println("City was deleted");
        } else {
            System.out.println("City was not deleted");
        }
    }
}
