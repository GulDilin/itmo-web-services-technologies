package guldilin.commands.find;

import guldilin.commands.common.EntitiesPrinter;
import guldilin.commands.common.Executor;
import guldilin.proxy.api.FieldIsNotFilterable_Exception;
import guldilin.proxy.api.FilterArgumentDTO;
import guldilin.proxy.api.PaginationDTO;
import guldilin.service.ServiceProvider;
import java.util.List;

/**
 * Executor for find command.
 */
public class FindExecutor extends Executor<FindArgs> {
    /**
     * Parse filter from filter string (field:operation:value).
     *
     * @param filter raw filter string value
     * @return parsed filter argument
     */
    private FilterArgumentDTO parseFilter(final String filter) {
        final int filterPartsSize = FilterOperation.FILTER_ARGUMENTS_PARTS;
        String[] filterParts = filter.split(":", filterPartsSize);

        var filterArgumentDTO = new FilterArgumentDTO();
        filterArgumentDTO.setField(filterParts[0]);
        filterArgumentDTO.setValue(filterParts[2]);
        return filterArgumentDTO;
    }

    /**
     * Parse filters list from parsed find command arguments.
     *
     * @param args parsed Find Command arguments object
     * @return parsed filters list
     */
    private List<FilterArgumentDTO> parseFilters(final FindArgs args) {
        return args.getFilters().stream().map(this::parseFilter).toList();
    }

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
     * @throws FieldIsNotFilterable_Exception for incorrect filter.
     */
    @Override
    public void execute(final String[] argv, final FindArgs args, final ServiceProvider serviceProvider)
            throws FieldIsNotFilterable_Exception {
        var pagination = args.toDTO();
        PaginationDTO cityResults = serviceProvider.provideCityService().findByFilter(parseFilters(args), pagination);
        EntitiesPrinter.print(System.out, cityResults);
    }
}
