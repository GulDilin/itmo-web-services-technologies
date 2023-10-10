package guldilin.config;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyKeyInfo implements Serializable {
    PropertiesKeys key;
    Boolean required;
    String defaultValue;
}
