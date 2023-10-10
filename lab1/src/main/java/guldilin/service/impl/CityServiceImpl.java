package guldilin.service.impl;

import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationInfoDTO;
import guldilin.entity.City;
import guldilin.repository.interfaces.CityRepository;
import guldilin.service.interfaces.CityService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

@WebService(serviceName = "CityService", targetNamespace = "http://localhost:8080/CityService")
public final class CityServiceImpl implements CityService {
    @Inject
    private CityRepository cityRepository;

    private CriteriaQuery<City> createFilterQuery() {
        try (EntityManager em = this.cityRepository.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<City> cityCriteria = cb.createQuery(City.class);
            Root<City> cityRoot = cityCriteria.from(City.class);
            cityCriteria.select(cityRoot);
            return cityCriteria;
        }
    }

    private CriteriaQuery<Long> createCounterQuery() {
        try (EntityManager em = this.cityRepository.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cityCriteria = cb.createQuery(Long.class);
            cityCriteria.select(cb.count(cityCriteria.from(City.class)));
            return cityCriteria;
        }
    }

    @Override
    @WebMethod
    public PaginationDTO<CityDTO> findByFilter(
            @WebParam(name = "filters") List<FilterArgumentDTO> filters,
            @WebParam(name = "pagination") PaginationInfoDTO pagination) {
        return PaginationDTO.<CityDTO>builder()
                .items(cityRepository.findByCriteria(this.createFilterQuery()).stream()
                        .map(City::mapToDTO)
                        .collect(Collectors.toList()))
                .total(cityRepository.countByCriteria(this.createCounterQuery()))
                .build();
    }
}
