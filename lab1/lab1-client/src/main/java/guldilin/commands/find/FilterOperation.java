package guldilin.commands.find;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for filter operation.
 */
@AllArgsConstructor
@Getter
public enum FilterOperation {
    /**
     * Equal operation.
     */
    EQUAL("=", "Equals");

    /**
     * Parts size of filter argument.
     */
    public static final int FILTER_ARGUMENTS_PARTS = 3;

    /**
     * Key that will be used in arguments.
     */
    private final String operationKey;

    /**
     * Operation description.
     */
    private final String description;

    /**
     * Get FilterOperation enum constant by operation key.
     *
     * @param key Operation key
     * @return FilterOperation enum constant
     */
    public static FilterOperation getByOperationKey(final String key) {
        return Arrays.stream(FilterOperation.class.getEnumConstants())
                .filter(it -> it.operationKey.equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("FilterOperation did not found by key: " + key));
    }
}
