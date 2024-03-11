package guldilin.service.interfaces;

import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import java.util.List;

/**
 * CityService interface.
 */
public interface CityService {
    /**
     * Find elements by field-value filters.
     *
     * @param filters List of field-value filters
     * @param pagination pagination information
     * @return Found elements
     */
    PaginationDTO<CityDTO> findByFilter(List<FilterArgumentDTO> filters, PaginationRequestDTO pagination)
            throws Exception;
}
