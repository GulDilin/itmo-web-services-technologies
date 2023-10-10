package guldilin.utils;

import guldilin.entity.FilterableField;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class FilterObjectConverter {
    public static List<String> getFilterableFields(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .peek(f -> f.setAccessible(true))
                .filter(f -> f.isAnnotationPresent(FilterableField.class))
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    //    public static List<FilterActionType> getAvailableFilterTypes(Class<?> cls) {
    //        Set<FilterActionType> forNumber =
    //                Stream.of(FilterActionType.LESS_THAN,
    //                        FilterActionType.GREATER_THAN,
    //                        FilterActionType.GREATER_THAN_OR_EQUALS,
    //                        FilterActionType.LESS_THAN_OR_EQUALS,
    //                        FilterActionType.EQUALS,
    //                        FilterActionType.IS_NULL
    //                ).collect(Collectors.toCollection(HashSet::new));
    //        Map<Class<?>, Set<FilterActionType>> map =
    //                Stream.of(
    //                                new Object[][] {
    //                                    {Integer.class, forNumber},
    //                                    {Date.class, forNumber},
    //                                    {String.class, full},
    //                                })
    //                        .collect(
    //                                Collectors.toMap(
    //                                        data -> (Class<?>) data[0],
    //                                        data -> (Set<FilterActionType>) data[1]));
    //        return Collections.emptyList();
    //    }
}
