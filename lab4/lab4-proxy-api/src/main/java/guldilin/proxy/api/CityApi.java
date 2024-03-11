package guldilin.proxy.api;

import guldilin.proxy.api.dto.CityDTO;
import guldilin.proxy.api.dto.PaginationDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * API interface for city entity management.
 */
@Path("/api/city")
@Produces(MediaType.APPLICATION_JSON)
public interface CityApi {
    /**
     * Find city paginated list by filter.
     *
     * @param name city name. Optional.
     * @param area city area. Optional.
     * @param population city population. Optional.
     * @param metersAboveSeaLevel city meters above sea level. Optional.
     * @param populationDensity city population density. Optional.
     * @param carCode city car code. Optional.
     * @param limit limit number of entities. Optional.
     * @param offset offset number of entities. Used for pagination. Optional
     * @return paginated entities list
     */
    @GET
    @Path("/")
    PaginationDTO<CityDTO> findByFilter(
            @QueryParam("name") String name,
            @QueryParam("area") Integer area,
            @QueryParam("population") Integer population,
            @QueryParam("meters_above_sea_level") Integer metersAboveSeaLevel,
            @QueryParam("population_density") Integer populationDensity,
            @QueryParam("car_code") Integer carCode,
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset);
}
