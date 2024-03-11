package guldilin.proxy.api;

import guldilin.proxy.api.dto.CityCreateUpdateDTO;
import guldilin.proxy.api.dto.CityDTO;
import guldilin.proxy.api.dto.PaginationDTO;
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

    /**
     * Create city.
     *
     * @param city new city data
     * @return created city
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    CityDTO create(CityCreateUpdateDTO city);

    /**
     * Update city.
     *
     * @param id id of city to update
     * @param city city data you want to update
     * @return updated city
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    CityDTO update(@PathParam("id") Integer id, CityCreateUpdateDTO city);

    /**
     * Update city part.
     *
     * @param id id of city to update
     * @param city city data you want to update
     * @return updated city
     */
    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    CityDTO patch(@PathParam("id") Integer id, CityCreateUpdateDTO city);

    /**
     * Delete city by id.
     *
     * @param id city id
     */
    @DELETE
    @Path("/{id}")
    void deleteById(@PathParam("id") Integer id);
}
