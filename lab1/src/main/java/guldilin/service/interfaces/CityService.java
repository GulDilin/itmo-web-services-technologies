package guldilin.service.interfaces;

import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.exceptions.FieldIsNotFilterable;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

@WebService
public interface CityService {
    /**
     * Find elements by field-value filters.
     *
     * @param filters List of field-value filters
     * @param pagination pagination information
     * @return Found elements
     * @throws FieldIsNotFilterable for incorrect filters argument
     */
    @WebMethod
    PaginationDTO<CityDTO> findByFilter(
            @WebParam(name = "filters") List<FilterArgumentDTO> filters,
            @WebParam(name = "pagination") PaginationRequestDTO pagination)
            throws FieldIsNotFilterable;
}
