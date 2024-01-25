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

@Path("/api/city")
@Produces(MediaType.APPLICATION_JSON)
public interface CityApi {
    /**
     * Find elements by field-value filters.
     *
     * @return Found elements
     */
    @GET
    @Path("/")
    PaginationDTO<CityDTO> findByFilter(
            @QueryParam("name") final String name,
            @QueryParam("area") final Integer area,
            @QueryParam("population") final Integer population,
            @QueryParam("meters_above_sea_level") final Integer metersAboveSeaLevel,
            @QueryParam("population_density") final Integer populationDensity,
            @QueryParam("car_code") final Integer carCode,
            @QueryParam("limit") final Integer limit,
            @QueryParam("offset") final Integer offset);

    /**
     * Create city.
     *
     * @param city new city data
     * @return created city
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    CityDTO create(final CityCreateUpdateDTO city);

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
    CityDTO update(@PathParam("id") final Integer id, final CityCreateUpdateDTO city);

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
    CityDTO patch(@PathParam("id") final Integer id, final CityCreateUpdateDTO city);

    /**
     * Delete city by id.
     *
     * @param id city id
     * @return delete status
     */
    @DELETE
    @Path("/{id}")
    Boolean deleteById(@PathParam("id") final Integer id);
}
