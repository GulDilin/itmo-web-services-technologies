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

@WebService(name = "City", serviceName = "CityService")
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
        var paginationV = Optional.ofNullable(pagination).orElse(new PaginationRequestDTO());
        Long nextOffset = null;
        var items = cityRepository.findByCriteria(cityRepository.createFilterQuery(filtersV), paginationV).stream()
                .map(City::mapToDTO)
                .collect(Collectors.toList());
        var total = cityRepository.countByCriteria(cityRepository.createCounterQuery());
        if (paginationV.getOffset() + items.size() < total) {
            nextOffset = (long) (paginationV.getOffset() + paginationV.getLimit());
        }
        return PaginationDTO.<CityDTO>builder()
                .items(items)
                .total(total)
                .nextOffset(nextOffset)
                .build();
    }
}
