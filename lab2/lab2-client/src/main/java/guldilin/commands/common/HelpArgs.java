package guldilin.commands.common;

import com.beust.jcommander.Parameter;
import java.util.List;
import lombok.Data;

/**
 * Arguments class for commands.
 */
@Data
public class HelpArgs {
    /**
     *
     */
    @Parameter(
            names = {"-help", "-h"},
            help = true,
            description = "Show help message (also can be used with -c argument to show help message for command)")
    private Boolean help = false;

    @Parameter(
            names = {"-c", "-command"},
            description = "Command name")
    private Command command;

    @Parameter
    private List<String> parameters;
}
