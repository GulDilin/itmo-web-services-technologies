package guldilin.entity;

import guldilin.utils.FilterableField;

import java.util.Collections;
import java.util.List;

public interface Filterable {
    static List<FilterableField<?>> getFilterableFields() {
        return Collections.emptyList();
    }
}
