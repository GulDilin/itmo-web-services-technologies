package guldilin.commands.common;

import com.beust.jcommander.Parameter;
import java.net.URL;
import lombok.Data;

/**
 * Arguments class for commands.
 */
@Data
public class Args {
    @Parameter(
            names = {"-help", "-h"},
            help = true,
            description = "Show help message (also can be used with -c argument to show help message for command)")
    private Boolean help = false;

    @Parameter(
            names = {"-c", "-command"},
            required = true,
            description = "Command name")
    private Command command;

    @Parameter(
            names = {"-url"},
            required = true,
            description = "Server URL base (example: http://localhost:8080)")
    private URL url;
}
