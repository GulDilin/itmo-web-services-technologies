package guldilin.commands.common;

import com.beust.jcommander.Parameter;
import lombok.Data;

/**
 * Arguments class for commands.
 */
@Data
public class Args {
    @Parameter(
            names = {"-c", "-command"},
            required = true,
            description = "Command name")
    private Command command;
}
