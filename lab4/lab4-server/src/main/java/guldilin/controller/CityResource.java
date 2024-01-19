package guldilin.controller;

import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.service.interfaces.CityService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequestScoped
@Path("/city")
@Produces(MediaType.APPLICATION_JSON)
public class CityResource {
    @Inject
    private CityService cityService;

    @GET
    @Path("/")
    @SneakyThrows
    public PaginationDTO<CityDTO> findByFilter(
            @QueryParam("name") final String name,
            @QueryParam("area") final Integer area,
            @QueryParam("population") final Integer population,
            @QueryParam("meters_above_sea_level") final Integer metersAboveSeaLevel,
            @QueryParam("population_density") final Integer populationDensity,
            @QueryParam("car_code") final Integer carCode,
            @QueryParam("limit") final Integer limit,
            @QueryParam("offset") final Integer offset
    ) {
        var paginationBuiler = PaginationRequestDTO.builder();
        Optional.ofNullable(limit).ifPresent(paginationBuiler::limit);
        Optional.ofNullable(offset).ifPresent(paginationBuiler::offset);
        var pagination = paginationBuiler.build();

        var filterMap = Map.of(
                "name", name,
                "area", area,
                "population", population,
                "metersAboveSeaLevel", metersAboveSeaLevel,
                "populationDensity", populationDensity,
                "carCode", carCode
        );
        var filters = filterMap.entrySet().stream()
                .filter(it -> Objects.isNull(it.getValue()))
                .map(it -> FilterArgumentDTO.builder().field(it.getKey()).value(it.getValue().toString()).build()).toList();

        return this.cityService.findByFilter(filters, pagination);
    }
}
