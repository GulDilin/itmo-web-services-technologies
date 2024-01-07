package guldilin.commands.common;

import java.util.Arrays;
import java.util.List;

/**
 * Command enum for cli commands.
 */
public enum Command {
    /**
     * Find command.
     */
    find,
    /**
     * Create command.
     */
    create,
    /**
     * Update command.
     */
    update,
    /**
     * Patch command.
     */
    patch,
    /**
     * Delete command.
     */
    delete;

    /**
     * Get enum names list.
     *
     * @return enum names
     */
    public static List<String> getNames() {
        return Arrays.stream(Command.class.getEnumConstants())
                .map(Command::name)
                .toList();
    }
}
