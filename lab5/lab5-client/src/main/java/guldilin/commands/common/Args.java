package guldilin.commands.common;

import com.beust.jcommander.Parameter;
import java.net.URL;
import lombok.Data;

/**
 * Arguments class for commands.
 */
@Data
public class Args {
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
            required = true,
            description = "Command name")
    private Command command;

    /**
     * Server URL.
     */
    @Parameter(
            names = {"-url"},
            required = true,
            description = "Server URL base (example: http://localhost:8080)")
    private URL url;
}
