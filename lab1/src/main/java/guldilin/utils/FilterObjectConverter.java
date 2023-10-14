package guldilin.utils;

import guldilin.entity.FilterableField;
import guldilin.exceptions.ErrorMessages;
import guldilin.exceptions.FieldIsNotFilterable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class FilterObjectConverter {

    /**
     * Empty private constructor for utility class.
     */
    private FilterObjectConverter() {
        // empty constructor
    }

    /**
     * Get List of field names annotated with @FilterableField from class.
     *
     * @param tClass Class to get fields
     * @return List of filterable field names
     */
    public static List<String> getFilterableFields(final Class<?> tClass) {
        final List<String> filterableFields = Arrays.stream(tClass.getDeclaredFields())
                .peek(f -> f.setAccessible(true))
                .filter(f -> f.isAnnotationPresent(FilterableField.class))
                .map(Field::getName)
                .collect(Collectors.toList());
        Optional.ofNullable(tClass.getSuperclass())
                .ifPresent((sClass) -> filterableFields.addAll(getFilterableFields(sClass)));
        return filterableFields;
    }

    /**
     * Check that field name is filterable for class.
     *
     * @param tClass Class to check filterable field
     * @param field field name to be checked
     * @throws FieldIsNotFilterable if tClass does not have field with provided
     * name or field isn't annotated with @FilterableField
     */
    public static void checkIfFieldFilterable(final Class<?> tClass, final String field) throws FieldIsNotFilterable {
        var filterableFields = Set.copyOf(FilterObjectConverter.getFilterableFields(tClass));
        if (!filterableFields.contains(field))
            throw new FieldIsNotFilterable(String.format("%s: %s", ErrorMessages.FIELD_NOT_FILTERABLE, field));
    }
}
