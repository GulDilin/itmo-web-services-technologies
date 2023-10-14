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
import java.util.List;
import java.util.stream.Collectors;

@WebService(serviceName = "CityService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public final class CityServiceImpl implements CityService {
    @Inject
    private CityRepository cityRepository;

    @Override
    @WebMethod
    public PaginationDTO<CityDTO> findByFilter(
            @WebParam final List<FilterArgumentDTO> filters, @WebParam final PaginationRequestDTO pagination)
            throws FieldIsNotFilterable {
        return PaginationDTO.<CityDTO>builder()
                .items(cityRepository.findByCriteria(cityRepository.createFilterQuery(filters), pagination).stream()
                        .map(City::mapToDTO)
                        .collect(Collectors.toList()))
                .total(cityRepository.countByCriteria(cityRepository.createCounterQuery()))
                .build();
    }
}
