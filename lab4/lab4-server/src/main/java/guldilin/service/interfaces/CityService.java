package guldilin.service.interfaces;

import guldilin.dto.CityCreateUpdateDTO;
import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.exceptions.EntryNotFound;
import guldilin.exceptions.FieldIsNotFilterable;
import guldilin.exceptions.ValidationFailed;
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
     * @throws FieldIsNotFilterable for incorrect filters argument
     */
    PaginationDTO<CityDTO> findByFilter(
            final List<FilterArgumentDTO> filters,
            final PaginationRequestDTO pagination)
            throws FieldIsNotFilterable, ValidationFailed;

    /**
     * Create city.
     *
     * @param city new city data
     * @return created city
     */
    CityDTO create(CityCreateUpdateDTO city) throws ValidationFailed;

    /**
     * Update city.
     *
     * @param id id of city to update
     * @param city city data you want to update
     * @return updated city
     */
    CityDTO update(final Integer id, final CityCreateUpdateDTO city)
            throws EntryNotFound, ValidationFailed;

    /**
     * Update city part.
     *
     * @param id id of city to update
     * @param city city data you want to update
     * @return updated city
     */
    CityDTO patch(final Integer id, final CityCreateUpdateDTO city)
            throws EntryNotFound, ValidationFailed;

    /**
     * Delete city by id.
     *
     * @param id city id
     * @return delete status
     */
    Boolean deleteById(Integer id) throws EntryNotFound, ValidationFailed;
}
