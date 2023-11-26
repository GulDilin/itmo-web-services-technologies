package guldilin.commands.delete;

import com.beust.jcommander.Parameter;
import guldilin.commands.common.Args;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteArgs extends Args {
    @Parameter(
            names = {"-id"},
            required = true,
            description = "City id")
    private Integer id;
}
