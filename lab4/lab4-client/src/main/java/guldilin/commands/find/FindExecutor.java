package guldilin.commands.find;

import guldilin.commands.common.EntitiesPrinter;
import guldilin.commands.common.Executor;
import guldilin.proxy.api.dto.CityDTO;
import guldilin.proxy.api.dto.PaginationDTO;
import guldilin.service.ServiceProvider;
import lombok.SneakyThrows;

/**
 * Executor for find command.
 */
public class FindExecutor extends Executor<FindArgs> {
    /**
     * Create empty FindArgs instance for parsing later.
     *
     * @return empty args object for executor
     */
    @Override
    public FindArgs createEmptyArgs() {
        return new FindArgs();
    }

    /**
     * Execute find command.
     *
     * @param args Parsed CLI args
     */
    @Override
    @SneakyThrows
    public void execute(final String[] argv, final FindArgs args, final ServiceProvider serviceProvider) {
        PaginationDTO<CityDTO> cityResults = serviceProvider
                .provideCityService()
                .findByFilter(
                        args.getName(),
                        args.getArea(),
                        args.getPopulation(),
                        args.getMetersAboveSeaLevel(),
                        args.getPopulationDensity(),
                        args.getCarCode(),
                        args.getLimit(),
                        args.getOffset());
        EntitiesPrinter.print(System.out, cityResults);
    }
}
