package guldilin.service.impl;

import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.entity.City;
import guldilin.repository.interfaces.CityRepository;
import guldilin.service.interfaces.CityService;
import guldilin.utils.Validator;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationDTO<CityDTO> findByFilter(
            final List<FilterArgumentDTO> filters, final PaginationRequestDTO pagination) throws Exception {
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
}
