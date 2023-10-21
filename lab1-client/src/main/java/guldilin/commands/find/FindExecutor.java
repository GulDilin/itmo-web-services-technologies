package guldilin.commands.find;

import com.beust.jcommander.JCommander;
import guldilin.commands.common.Executor;
import guldilin.service.impl.CityService;
import guldilin.service.impl.CityService_Service;
import guldilin.service.impl.FieldIsNotFilterable;
import guldilin.service.interfaces.PaginationDTO;
import java.util.Collections;

/**
 * Executor for find command.
 */
public class FindExecutor implements Executor {
    /**
     * Execute find command.
     *
     * @param argv Unparsed CLI args
     * @throws FieldIsNotFilterable for incorrect filter.
     */
    @Override
    public void execute(final String[] argv) throws FieldIsNotFilterable {
        FindArgs args = parseArgs(argv);
        CityService_Service service = new CityService_Service();
        CityService cityService = service.getCityPort();
        PaginationDTO cityResults = cityService.findByFilter(Collections.emptyList(), null);
        System.out.println(cityResults);
        cityResults.getItems().forEach(System.out::println);
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
