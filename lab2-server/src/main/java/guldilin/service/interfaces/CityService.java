package guldilin.service.interfaces;

import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.exceptions.FieldIsNotFilterable;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.validation.Valid;
import java.util.List;

@WebService(serviceName = "CityService", targetNamespace = "http://service.guldilin")
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
            @WebParam(name = "pagination") @Valid PaginationRequestDTO pagination)
            throws FieldIsNotFilterable;

    /**
     * Create city.
     *
     * @param name city name
     * @param area area information
     * @param population population information
     * @param metersAboveSeaLevel metersAboveSeaLevel information
     * @param populationDensity populationDensity information
     * @param carCode carCode information
     * @return CityDTO
     */
    @WebMethod
    CityDTO createCity(
            @WebParam(name = "name") String name,
            @WebParam(name = "area") Integer area,
            @WebParam(name = "population") Integer population,
            @WebParam(name = "metersAboveSeaLevel") Integer metersAboveSeaLevel,
            @WebParam(name = "populationDensity") Integer populationDensity,
            @WebParam(name = "carCode") Integer carCode);

    /**
     * Find elements by field-value filters.
     *
     * @param name city name
     * @param area area information
     * @param population population information
     * @param metersAboveSeaLevel metersAboveSeaLevel information
     * @param populationDensity populationDensity information
     * @param carCode carCode information
     */
    @WebMethod
    CityDTO updateCity(
            @WebParam(name = "name") String name,
            @WebParam(name = "area") Integer area,
            @WebParam(name = "population") Integer population,
            @WebParam(name = "metersAboveSeaLevel") Integer metersAboveSeaLevel,
            @WebParam(name = "populationDensity") Integer populationDensity,
            @WebParam(name = "carCode") Integer carCode);

    /**
     * Find elements by field-value filters.
     *
     * @param cityId city id
     * @return delete status
     */
    @WebMethod
    boolean deleteById(
            @WebParam(name = "id") String cityId);
}
