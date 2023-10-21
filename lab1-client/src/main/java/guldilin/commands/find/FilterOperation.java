package guldilin.commands.find;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FilterOperation {
    EQUAL("=", "Equals");

    public static final int FILTER_ARGUMENTS_PARTS = 3;
    private final String operationKey;
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
