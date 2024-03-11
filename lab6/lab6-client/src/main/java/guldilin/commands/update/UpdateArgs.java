package guldilin.commands.update;

import com.beust.jcommander.Parameter;
import guldilin.commands.create.CreateArgs;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Args class for update command.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateArgs extends CreateArgs {
    /**
     * Identifier of city to update.
     */
    @Parameter(
            names = {"-id"},
            required = true,
            description = "City id")
    private Integer id;
}
