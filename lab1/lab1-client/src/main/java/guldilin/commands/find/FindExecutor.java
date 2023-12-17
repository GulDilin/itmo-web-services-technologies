package guldilin.commands.find;

import com.beust.jcommander.JCommander;
import guldilin.commands.common.EntitiesPrinter;
import guldilin.commands.common.Executor;
import guldilin.proxy.api.CityService;
import guldilin.proxy.api.CityWs;
import guldilin.proxy.api.FieldIsNotFilterable_Exception;
import guldilin.proxy.api.FilterArgumentDTO;
import guldilin.proxy.api.PaginationDTO;
import guldilin.proxy.api.PaginationRequestDTO;
import java.net.MalformedURLException;
import java.net.URL;
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
     * @throws FieldIsNotFilterable_Exception for incorrect filter.
     */
    @Override
    public void execute(final String[] argv) throws FieldIsNotFilterable_Exception {
        FindArgs args = parseArgs(argv);

        if (args.getHelp()) {
            buildCommander().usage();
            return;
        }

        var pagination = new PaginationRequestDTO();
        pagination.setLimit(args.getLimit());
        pagination.setOffset(args.getOffset());

        try {
            var url = new URL(String.format("%s/CityService?wsdl", args.getUrl()));
            CityService service = new CityService(url);
            CityWs cityService = service.getCityPort();
            PaginationDTO cityResults = cityService.findByFilter(parseFilters(args), pagination);

            EntitiesPrinter.print(System.out, cityResults);
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Build JCommander arguments parser.
     *
     * @return JCommander object
     */
    @Override
    public JCommander buildCommander() {
        return JCommander.newBuilder()
                .programName("lab1-client.jar")
                .addObject(new FindArgs())
                .build();
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
