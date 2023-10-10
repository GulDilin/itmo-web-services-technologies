package guldilin.entity;

import guldilin.utils.FilterableFieldInfo;
import java.util.Collections;
import java.util.List;

public interface Filterable {
    static List<FilterableFieldInfo<?>> getFilterableFields() {
        return Collections.emptyList();
    }
}
