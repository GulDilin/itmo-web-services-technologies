package guldilin.commands.find;

import com.beust.jcommander.JCommander;
import guldilin.commands.common.EntitiesPrinter;
import guldilin.commands.common.Executor;
import guldilin.service.impl.CityService;
import guldilin.service.impl.CityService_Service;
import guldilin.service.impl.FieldIsNotFilterable;
import guldilin.service.interfaces.FilterArgumentDTO;
import guldilin.service.interfaces.PaginationDTO;
import guldilin.service.interfaces.PaginationRequestDTO;
import java.util.List;

/**
 * Executor for find command.
 */
public class FindExecutor implements Executor {
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
     * Execute find command.
     *
     * @param argv Unparsed CLI args
     * @throws FieldIsNotFilterable for incorrect filter.
     */
    @Override
    public void execute(final String[] argv) throws FieldIsNotFilterable {
        FindArgs args = parseArgs(argv);

        var pagination = new PaginationRequestDTO();
        pagination.setLimit(args.getLimit());
        pagination.setOffset(args.getOffset());

        CityService_Service service = new CityService_Service();
        CityService cityService = service.getCityPort();
        PaginationDTO cityResults = cityService.findByFilter(parseFilters(args), pagination);

        EntitiesPrinter.print(System.out, cityResults);
    }

    /**
     * Parse Find command arguments.
     *
     * @param argv Unparsed CLI args
     * @return parsed args
     */
    @Override
    public FindArgs parseArgs(final String[] argv) {
        FindArgs args = new FindArgs();
        JCommander.newBuilder().addObject(args).build().parse(argv);
        return args;
    }
}
