package guldilin.service.impl;

import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.entity.City;
import guldilin.exceptions.FieldIsNotFilterable;
import guldilin.repository.interfaces.CityRepository;
import guldilin.service.interfaces.CityService;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebService(name = "City", serviceName = "CityService", targetNamespace = "http://service.guldilin")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class CityServiceImpl implements CityService {
    @Inject
    private CityRepository cityRepository;

    /**
     * Find elements by field-value filters.
     *
     * @param filters    List of field-value filters
     * @param pagination pagination information
     * @return Found elements
     * @throws FieldIsNotFilterable for incorrect filters argument
     */
    @Override
    @WebMethod
    public PaginationDTO<CityDTO> findByFilter(
            @WebParam(name = "filters") final List<FilterArgumentDTO> filters,
            @WebParam(name = "pagination") @Valid final PaginationRequestDTO pagination)
            throws FieldIsNotFilterable {
        var filtersV = Optional.ofNullable(filters).orElse(Collections.emptyList());
        System.out.println(filtersV);
        var paginationV = Optional.ofNullable(pagination).orElse(new PaginationRequestDTO());
        Long nextOffset = null;
        var items = cityRepository.findByCriteria(cityRepository.createFilterQuery(filtersV), paginationV).stream()
                .map(City::mapToDTO)
                .collect(Collectors.toList());
        var total = cityRepository.countByCriteria(cityRepository.createCounterQuery(filtersV));
        if (paginationV.getOffset() + items.size() < total) {
            nextOffset = (long) (paginationV.getOffset() + paginationV.getLimit());
        }
        return PaginationDTO.<CityDTO>builder()
                .items(items)
                .total(total)
                .nextOffset(nextOffset)
                .build();
    }

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
    @Override
    @WebMethod
    public CityDTO createCity(
            @WebParam(name = "name") String name,
            @WebParam(name = "area") Integer area,
            @WebParam(name = "population") Integer population,
            @WebParam(name = "metersAboveSeaLevel") Integer metersAboveSeaLevel,
            @WebParam(name = "populationDensity") Integer populationDensity,
            @WebParam(name = "carCode") Integer carCode){
        return new CityDTO();
    }

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
    @Override
    @WebMethod
    public CityDTO updateCity(
            @WebParam(name = "name") String name,
            @WebParam(name = "area") Integer area,
            @WebParam(name = "population") Integer population,
            @WebParam(name = "metersAboveSeaLevel") Integer metersAboveSeaLevel,
            @WebParam(name = "populationDensity") Integer populationDensity,
            @WebParam(name = "carCode") Integer carCode){
        return new CityDTO();
    }

    /**
     * Find elements by field-value filters.
     *
     * @param cityId city id
     * @return delete status
     */
    @Override
    @WebMethod
    public boolean deleteById(
            @WebParam(name = "id") String cityId){
        return true;
    }
}
