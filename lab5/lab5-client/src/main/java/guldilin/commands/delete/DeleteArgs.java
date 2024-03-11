package guldilin.commands.delete;

import com.beust.jcommander.Parameter;
import guldilin.commands.common.Args;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Arguments class for delete command.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteArgs extends Args {
    /**
     * Identifier of city to delete.
     */
    @Parameter(
            names = {"-id"},
            required = true,
            description = "City id")
    private Integer id;
}
