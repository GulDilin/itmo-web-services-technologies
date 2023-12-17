package guldilin.service.impl;

import guldilin.dto.CityCreateUpdateDTO;
import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.entity.City;
import guldilin.exceptions.EntryNotFound;
import guldilin.exceptions.FieldIsNotFilterable;
import guldilin.repository.interfaces.CityRepository;
import guldilin.service.interfaces.CityService;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.validation.Valid;
import jakarta.xml.ws.Action;
import jakarta.xml.ws.FaultAction;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebService(
        name = "CityWs",
        serviceName = "CityService",
        targetNamespace = "http://service.guldilin",
        portName = "CityPort",
        wsdlLocation = "META-INF/wsdl/CityService.wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class CityServiceImpl implements CityService {
    @Inject
    private CityRepository cityRepository;

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @WebMethod
    @Action(
            input = "http://service.guldilin/City/createRequest",
            output = "http://service.guldilin/City/createResponse")
    public CityDTO create(@WebParam(name = "city") final CityCreateUpdateDTO city) {
        return this.cityRepository.create(city.mapToEntity()).mapToDTO();
    }

    /**
     * {@inheritDoc}
     */
    @WebMethod
    @Action(
            input = "http://service.guldilin/City/updateRequest",
            output = "http://service.guldilin/City/updateResponse",
            fault = {
                @FaultAction(
                        className = EntryNotFound.class,
                        value = "http://service.guldilin/City/update/Fault/EntryNotFound")
            })
    public CityDTO update(
            @WebParam(name = "id") final Integer id, @WebParam(name = "city") final CityCreateUpdateDTO city)
            throws EntryNotFound {
        City cityEntry = this.cityRepository.getById(id);
        city.updateEntity(cityEntry);
        return this.cityRepository.update(cityEntry).mapToDTO();
    }

    /**
     * {@inheritDoc}
     */
    @WebMethod
    @Action(
            input = "http://service.guldilin/City/patchRequest",
            output = "http://service.guldilin/City/patchResponse",
            fault = {
                @FaultAction(
                        className = EntryNotFound.class,
                        value = "http://service.guldilin/City/patch/Fault/EntryNotFound")
            })
    public CityDTO patch(
            @WebParam(name = "id") final Integer id, @WebParam(name = "city") final CityCreateUpdateDTO city)
            throws EntryNotFound {
        City cityEntry = this.cityRepository.getById(id);
        city.patchEntity(cityEntry);
        return this.cityRepository.update(cityEntry).mapToDTO();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @Action(
            input = "http://service.guldilin/City/deleteByIdRequest",
            output = "http://service.guldilin/City/deleteByIdResponse")
    public Boolean deleteById(@WebParam(name = "id") final Integer id) {
        try {
            this.cityRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
