package guldilin.commands.find;

import com.beust.jcommander.Parameter;
import guldilin.commands.common.Args;
import guldilin.proxy.api.PaginationRequestDTO;
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
    /**
     * Filter list.
     */
    @Parameter(
            names = {"-filter", "-f"},
            description = "Filters for read command in format field:operation:value (area:=:1)",
            validateWith = {FindFilterArgumentValidator.class})
    private List<String> filters = new ArrayList<>();

    /**
     * Items limit.
     */
    @Parameter(
            names = {"-limit"},
            description = "Results limit")
    private Integer limit;

    /**
     * Items offset.
     */
    @Parameter(
            names = {"-offset"},
            description = "Results offset")
    private Integer offset;

    /**
     * Convert arguments to DTO for service usage.
     *
     * @return PaginationRequestDTO instance
     */
    public PaginationRequestDTO toDTO() {
        var pagination = new PaginationRequestDTO();
        pagination.setLimit(this.limit);
        pagination.setOffset(this.offset);
        return pagination;
    }
}
