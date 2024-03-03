package guldilin.service.impl;

import guldilin.dto.CityCreateUpdateDTO;
import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.entity.City;
import guldilin.exceptions.EntryNotFound;
import guldilin.exceptions.FieldIsNotFilterable;
import guldilin.exceptions.ValidationFailed;
import guldilin.repository.interfaces.CityRepository;
import guldilin.service.interfaces.CityService;
import guldilin.utils.Validator;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.validation.ConstraintViolationException;
import jakarta.xml.ws.Action;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import org.apache.juddi.v3.annotations.UDDIService;
import org.apache.juddi.v3.annotations.UDDIServiceBinding;

/**
 * Implementation for CityService.
 */
@UDDIService(businessKey = "uddi:tws", serviceKey = "uddi:CityWs", description = "City management service")
@UDDIServiceBinding(
        bindingKey = "uddi:CityWsBinding",
        description = "WSDL endpoint for the City Service",
        accessPoint = "http://localhost:8080/lab7-server/CityService?wsdl")
@WebService(
        name = "CityWs",
        serviceName = "CityService",
        targetNamespace = "http://service.guldilin",
        portName = "CityPort",
        wsdlLocation = "META-INF/wsdl/CityService.wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@NoArgsConstructor
public class CityServiceImpl implements CityService {
    /**
     * City repository implementation. Auto-injected.
     */
    @Inject
    private CityRepository cityRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    public PaginationDTO<CityDTO> findByFilter(
            @WebParam(name = "filters") final List<FilterArgumentDTO> filters,
            @WebParam(name = "pagination") final PaginationRequestDTO pagination)
            throws FieldIsNotFilterable, ValidationFailed {
        Validator.validate(pagination);
        var filtersV = Optional.ofNullable(filters).orElse(Collections.emptyList());
        var paginationV = Optional.ofNullable(pagination).orElse(new PaginationRequestDTO());
        var items = cityRepository.findByCriteria(cityRepository.createFilterQuery(filtersV), paginationV).stream()
                .map(City::mapToDTO)
                .collect(Collectors.toList());
        var total = cityRepository.countByCriteria(cityRepository.createCounterQuery(filtersV));
        Long nextOffset = null;
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
    @Override
    @WebMethod
    @Action(
            input = "http://service.guldilin/City/createRequest",
            output = "http://service.guldilin/City/createResponse")
    public CityDTO create(@WebParam(name = "city") final CityCreateUpdateDTO city) throws ValidationFailed {
        Validator.validateNotNull(city, "city");
        try {
            return this.cityRepository.create(city.mapToEntity()).mapToDTO();
        } catch (ConstraintViolationException e) {
            throw new ValidationFailed(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @Action(
            input = "http://service.guldilin/City/updateRequest",
            output = "http://service.guldilin/City/updateResponse")
    public CityDTO update(
            @WebParam(name = "id") final Integer id, @WebParam(name = "city") final CityCreateUpdateDTO city)
            throws EntryNotFound, ValidationFailed {
        Validator.validateNotNull(id, "id");
        Validator.validateNotNull(city, "city");
        City cityEntry = this.cityRepository.getById(id);
        city.updateEntity(cityEntry);
        try {
            return this.cityRepository.update(cityEntry).mapToDTO();
        } catch (ConstraintViolationException e) {
            throw new ValidationFailed(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @Action(input = "http://service.guldilin/City/patchRequest", output = "http://service.guldilin/City/patchResponse")
    public CityDTO patch(
            @WebParam(name = "id") final Integer id, @WebParam(name = "city") final CityCreateUpdateDTO city)
            throws EntryNotFound, ValidationFailed {
        Validator.validateNotNull(id, "id");
        Validator.validateNotNull(city, "city");
        City cityEntry = this.cityRepository.getById(id);
        city.patchEntity(cityEntry);
        try {
            return this.cityRepository.update(cityEntry).mapToDTO();
        } catch (ConstraintViolationException e) {
            throw new ValidationFailed(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    @Action(
            input = "http://service.guldilin/City/deleteByIdRequest",
            output = "http://service.guldilin/City/deleteByIdResponse")
    public Boolean deleteById(@WebParam(name = "id") final Integer id) throws EntryNotFound, ValidationFailed {
        Validator.validateNotNull(id, "id");
        this.cityRepository.deleteById(id);
        return true;
    }
}
