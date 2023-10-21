package guldilin.commands.find;

import com.beust.jcommander.Parameter;
import guldilin.commands.common.Args;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Args class for find command.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FindArgs extends Args {
    @Parameter(
            names = {"-filter", "-f"},
            description = "Filters for read command in format field:operation:value (area:=:1)")
    private List<String> filters = new ArrayList<>();

    @Parameter(
            names = {"-limit"},
            description = "Results limit")
    private Integer limit;

    @Parameter(
            names = {"-offset"},
            description = "Results offset")
    private Integer offset;
}
