package guldilin.commands.main;

import com.beust.jcommander.Parameter;
import guldilin.commands.common.Args;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Arguments class for main command.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MainArgs extends Args {
    @Parameter
    private List<String> parameters;
}
