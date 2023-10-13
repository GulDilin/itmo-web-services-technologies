package guldilin.dto;

import java.util.List;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaginationCityDTO extends PaginationDTO<CityDTO> {
    public PaginationCityDTO(Long total, Long nextOffset, List<CityDTO> items) {
        super(total, nextOffset, items);
    }
}
