package guldilin.dto;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterArgumentDTO implements Serializable {
    private String field;
    private String value;
}
