package guldilin.service.interfaces;

import guldilin.dto.CityCreateUpdateDTO;
import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.exceptions.EntryNotFound;
import guldilin.exceptions.FieldIsNotFilterable;
import guldilin.exceptions.ValidationFailed;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

/**
 * CityService interface.
 */
@WebService(
        name = "CityWs",
        serviceName = "CityService",
        targetNamespace = "http://service.guldilin",
        portName = "CityPort",
        wsdlLocation = "META-INF/wsdl/CityService.wsdl")
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
            throws FieldIsNotFilterable, ValidationFailed;

    /**
     * Create city.
     *
     * @param city new city data
     * @return created city
     */
    @WebMethod
    CityDTO create(@WebParam(name = "city") CityCreateUpdateDTO city) throws ValidationFailed;

    /**
     * Update city.
     *
     * @param id id of city to update
     * @param city city data you want to update
     * @return updated city
     */
    @WebMethod
    CityDTO update(@WebParam(name = "id") Integer id, @WebParam(name = "city") CityCreateUpdateDTO city)
            throws EntryNotFound, ValidationFailed;

    /**
     * Update city part.
     *
     * @param id id of city to update
     * @param city city data you want to update
     * @return updated city
     */
    @WebMethod
    CityDTO patch(@WebParam(name = "id") Integer id, @WebParam(name = "city") CityCreateUpdateDTO city)
            throws EntryNotFound, ValidationFailed;

    /**
     * Delete city by id.
     *
     * @param id city id
     * @return delete status
     */
    @WebMethod
    Boolean deleteById(@WebParam(name = "id") Integer id) throws EntryNotFound, ValidationFailed;
}
