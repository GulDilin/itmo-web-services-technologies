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
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation for CityService.
 */
@NoArgsConstructor
@Startup
@Singleton
public class CityServiceImpl implements CityService {
    /**
     * City repository implementation. Auto-injected.
     */
    @Inject
    private CityRepository cityRepository;

    private static final Logger LOGGER = LogManager.getLogger(CityServiceImpl.class);

    @PostConstruct
    public void init() {
        LOGGER.info("CityServiceImpl init");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationDTO<CityDTO> findByFilter(
            final List<FilterArgumentDTO> filters, final PaginationRequestDTO pagination)
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
    public CityDTO create(final CityCreateUpdateDTO city) throws ValidationFailed {
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
    public CityDTO update(final Integer id, final CityCreateUpdateDTO city) throws EntryNotFound, ValidationFailed {
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
    public CityDTO patch(final Integer id, final CityCreateUpdateDTO city) throws EntryNotFound, ValidationFailed {
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
    public Boolean deleteById(final Integer id) throws EntryNotFound, ValidationFailed {
        Validator.validateNotNull(id, "id");
        this.cityRepository.deleteById(id);
        return true;
    }
}
