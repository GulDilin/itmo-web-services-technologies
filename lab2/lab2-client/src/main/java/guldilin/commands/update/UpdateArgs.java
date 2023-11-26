package guldilin.commands.update;

import com.beust.jcommander.Parameter;
import guldilin.commands.create.CreateArgs;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateArgs extends CreateArgs {
    @Parameter(
            names = {"-id"},
            required = true,
            description = "City id")
    private Integer id;
}
