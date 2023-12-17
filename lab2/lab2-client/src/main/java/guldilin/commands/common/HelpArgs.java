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
     * Help argument. If set - help message will be shown and command won't be executed.
     * Can be used with command to show command help message.
     */
    @Parameter(
            names = {"-help", "-h"},
            help = true,
            description = "Show help message (also can be used with -c argument to show help message for command)")
    private Boolean help = false;

    /**
     * Command name.
     */
    @Parameter(
            names = {"-c", "-command"},
            description = "Command name")
    private Command command;

    /**
     * Any other parameters.
     */
    @Parameter
    private List<String> parameters;
}
