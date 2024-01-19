package guldilin.controller;

import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.service.interfaces.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Find cities by filters",
            tags = {"city"},
            description = "Returns a list of cities",
            responses = {
                    @ApiResponse(description = "The city list", content = @Content(
                            schema = @Schema(implementation = PaginationDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
            })
    public PaginationDTO<CityDTO> findByFilter(
            @Parameter(description = "City name") @QueryParam("name") final String name,
            @Parameter(description = "City area") @QueryParam("area") final Integer area,
            @Parameter(description = "City population") @QueryParam("population") final Integer population,
            @Parameter(description = "City meters_above_sea_level") @QueryParam("meters_above_sea_level") final Integer metersAboveSeaLevel,
            @Parameter(description = "City population_density") @QueryParam("population_density") final Integer populationDensity,
            @Parameter(description = "City car_code") @QueryParam("car_code") final Integer carCode,
            @Parameter(description = "City limit") @QueryParam("limit") final Integer limit,
            @Parameter(description = "City offset") @QueryParam("offset") final Integer offset
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
