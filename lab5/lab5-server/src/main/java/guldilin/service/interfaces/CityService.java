package guldilin.service.interfaces;

import guldilin.dto.CityCreateUpdateDTO;
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
     * @throws Exception for incorrect filters argument
     */
    PaginationDTO<CityDTO> findByFilter(List<FilterArgumentDTO> filters, PaginationRequestDTO pagination)
            throws Exception, Exception;

    /**
     * Create city.
     *
     * @param city new city data
     * @return created city
     */
    CityDTO create(CityCreateUpdateDTO city) throws Exception;

    /**
     * Update city.
     *
     * @param id id of city to update
     * @param city city data you want to update
     * @return updated city
     */
    CityDTO update(Integer id, CityCreateUpdateDTO city) throws Exception;

    /**
     * Update city part.
     *
     * @param id id of city to update
     * @param city city data you want to update
     * @return updated city
     */
    CityDTO patch(Integer id, CityCreateUpdateDTO city) throws Exception;

    /**
     * Delete city by id.
     *
     * @param id city id
     * @return delete status
     */
    Boolean deleteById(Integer id) throws Exception;
}
