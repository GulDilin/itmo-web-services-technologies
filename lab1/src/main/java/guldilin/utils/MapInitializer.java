package guldilin.utils;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapInitializer<K, V> {
    public Map<K, V> init(Object[][] initData) {
        return Stream.of(initData).collect(Collectors.toMap(data -> (K) data[0], data -> (V) data[1]));
    }
}
