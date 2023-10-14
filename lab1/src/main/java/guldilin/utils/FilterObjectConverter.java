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

public class FilterObjectConverter {
    public static List<String> getFilterableFields(Object object) {
        return getFilterableFields(object.getClass());
    }

    public static List<String> getFilterableFields(Class<?> tClass) {
        final List<String> filterableFields = Arrays.stream(tClass.getDeclaredFields())
                .peek(f -> f.setAccessible(true))
                .filter(f -> f.isAnnotationPresent(FilterableField.class))
                .map(Field::getName)
                .collect(Collectors.toList());
        Optional.ofNullable(tClass.getSuperclass())
                .ifPresent((sClass) -> filterableFields.addAll(getFilterableFields(sClass)));
        return filterableFields;
    }

    public static void checkIfFieldFilterable(Class<?> tClass, String field) throws FieldIsNotFilterable {
        var filterableFields = Set.copyOf(FilterObjectConverter.getFilterableFields(tClass));
        if (!filterableFields.contains(field))
            throw new FieldIsNotFilterable(String.format("%s: %s", ErrorMessages.FIELD_NOT_FILTERABLE, field));
    }
}
