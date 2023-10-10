package guldilin.utils;

import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterableFieldInfo<T> {
    private Class<T> tClass;
    private FilterActionType actionType;
    private String name;
    private Function<String, T> parser = s -> (T) s;
}
