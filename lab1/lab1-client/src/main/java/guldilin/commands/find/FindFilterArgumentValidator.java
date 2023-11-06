package guldilin.commands.find;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FindFilterArgumentValidator implements IParameterValidator {

    /**
     * Validate Filter arguments of find command.
     *
     * @param name arg name
     * @param value arg value
     * @throws ParameterException if filter argument is not correct
     */
    @Override
    public void validate(final String name, final String value) throws ParameterException {
        var filterParts = value.split(":", FilterOperation.FILTER_ARGUMENTS_PARTS);
        if (filterParts.length < FilterOperation.FILTER_ARGUMENTS_PARTS) {
            throw new ParameterException("Incorrect filter format. Allowed format: \"field:operation:value\"");
        }
        try {
            var operation = filterParts[1];
            FilterOperation.getByOperationKey(operation);
        } catch (IllegalArgumentException e) {
            String allowedOperations = Arrays.stream(FilterOperation.class.getEnumConstants())
                    .map(it -> String.format("\"%s\" (%s)", it.getOperationKey(), it.getDescription()))
                    .collect(Collectors.joining(", "));

            throw new ParameterException(String.format(
                    """
                    Incorrect filter format (field:operation:value).
                    Operation is not allowed. Allowed operations: [%s]""",
                    allowedOperations));
        }
    }
}
