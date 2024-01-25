package guldilin.controller;

import guldilin.dto.CityCreateUpdateDTO;
import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.service.interfaces.CityService;
import guldilin.utils.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequestScoped
@Path("/api/city")
@Produces(MediaType.APPLICATION_JSON)
public class CityResource {
    private static final Logger LOGGER = LogManager.getLogger(CityResource.class);

    @Inject
    private CityService cityService;

    @GET
    @Path("/")
    @SneakyThrows
    @Operation(
            summary = "Find cities by filters",
            tags = {"city"},
            description = "Returns a list of cities",
            responses = {
                @ApiResponse(
                        description = "The city list",
                        content = @Content(schema = @Schema(implementation = PaginationDTO.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request data"),
            })
    public PaginationDTO<CityDTO> findByFilter(
            @Parameter(description = "City name") @QueryParam("name") final String name,
            @Parameter(description = "City area") @QueryParam("area") final Integer area,
            @Parameter(description = "City population") @QueryParam("population") final Integer population,
            @Parameter(description = "City meters_above_sea_level") @QueryParam("meters_above_sea_level")
                    final Integer metersAboveSeaLevel,
            @Parameter(description = "City population_density") @QueryParam("population_density")
                    final Integer populationDensity,
            @Parameter(description = "City car_code") @QueryParam("car_code") final Integer carCode,
            @Parameter(description = "City limit") @QueryParam("limit") final Integer limit,
            @Parameter(description = "City offset") @QueryParam("offset") final Integer offset) {
        LOGGER.info("Run findByFilter");
        var pagination = PaginationRequestDTO.builder()
                .limit(Optional.ofNullable(limit).orElse(PaginationRequestDTO.DEFAULT_LIMIT))
                .offset(Optional.ofNullable(offset).orElse(PaginationRequestDTO.DEFAULT_OFFSET))
                .build();
        Validator.validate(pagination);
        var filterMap = new HashMap<String, Object>() {
            {
                put("name", name);
                put("area", area);
                put("population", population);
                put("metersAboveSeaLevel", metersAboveSeaLevel);
                put("populationDensity", populationDensity);
                put("carCode", carCode);
            }
        };
        var filters = filterMap.entrySet().stream()
                .filter(it -> Objects.nonNull(it.getValue()))
                .map(it -> FilterArgumentDTO.builder()
                        .field(it.getKey())
                        .value(it.getValue().toString())
                        .build())
                .toList();

        return this.cityService.findByFilter(filters, pagination);
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @SneakyThrows
    @Operation(
            summary = "Creates a city",
            tags = {"city"},
            description = "Create a city",
            responses = {
                @ApiResponse(
                        description = "The city",
                        content = @Content(schema = @Schema(implementation = CityDTO.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request data"),
            })
    public CityDTO create(
            @RequestBody(
                            description = "City to update",
                            required = true,
                            content = @Content(schema = @Schema(implementation = CityCreateUpdateDTO.class)))
                    final CityCreateUpdateDTO city) {
        return this.cityService.create(city);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @SneakyThrows
    @Operation(
            summary = "Update the city",
            tags = {"city"},
            description = "Update the city with full schema",
            responses = {
                @ApiResponse(
                        description = "The city",
                        content = @Content(schema = @Schema(implementation = CityDTO.class))),
                @ApiResponse(responseCode = "404", description = "City not found"),
                @ApiResponse(responseCode = "400", description = "Invalid request data"),
            })
    public CityDTO update(
            @Parameter(description = "City id", required = true) @PathParam("id") final Integer id,
            @RequestBody(
                            description = "City to update",
                            required = true,
                            content = @Content(schema = @Schema(implementation = CityCreateUpdateDTO.class)))
                    final CityCreateUpdateDTO city) {
        return this.cityService.update(id, city);
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @SneakyThrows
    @Operation(
            summary = "Patch the city",
            tags = {"city"},
            description = "Patch the city partially",
            responses = {
                @ApiResponse(
                        description = "The city",
                        content = @Content(schema = @Schema(implementation = CityDTO.class))),
                @ApiResponse(responseCode = "404", description = "City not found"),
                @ApiResponse(responseCode = "400", description = "Invalid request data"),
            })
    public CityDTO patch(
            @Parameter(description = "City id", required = true) @PathParam("id") final Integer id,
            @RequestBody(
                            description = "City to patch",
                            required = true,
                            content = @Content(schema = @Schema(implementation = CityCreateUpdateDTO.class)))
                    final CityCreateUpdateDTO city) {
        return this.cityService.patch(id, city);
    }

    @DELETE
    @Path("/{id}")
    @SneakyThrows
    @Operation(
            summary = "Delete the city",
            tags = {"city"},
            description = "Delete the city",
            responses = {
                @ApiResponse(description = "The city deletion status"),
                @ApiResponse(responseCode = "404", description = "City not found"),
                @ApiResponse(responseCode = "400", description = "Invalid request data"),
            })
    public Response delete(@Parameter(description = "City id", required = true) @PathParam("id") final Integer id) {
        this.cityService.deleteById(id);
        return Response.ok().build();
    }
}
