package guldilin.commands.patch;

import com.beust.jcommander.Parameter;
import guldilin.commands.common.Args;
import guldilin.proxy.api.dto.CityCreateUpdateDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Arguments class for patch command.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PatchArgs extends Args {
    /**
     * Identifier of city to update.
     */
    @Parameter(
            names = {"-id"},
            required = true,
            description = "City id")
    private Integer id;

    /**
     * City name.
     * {@link guldilin.proxy.api.dto.CityDTO#getName()}
     */
    @Parameter(
            names = {"-name"},
            description = "City name")
    private String name;

    /**
     * City area.
     * {@link guldilin.proxy.api.dto.CityDTO#getArea()}
     */
    @Parameter(
            names = {"-area"},
            description = "City area number")
    private Integer area;

    /**
     * City population.
     * {@link guldilin.proxy.api.dto.CityDTO#getPopulation()}
     */
    @Parameter(
            names = {"-population"},
            description = "City population value")
    private Integer population;

    /**
     * City metersAboveSeaLevel.
     * {@link guldilin.proxy.api.dto.CityDTO#getMetersAboveSeaLevel()}
     */
    @Parameter(
            names = {"-meters-above-sea-level"},
            description = "Meters above sea level for City")
    private Float metersAboveSeaLevel;

    /**
     * City populationDensity.
     * {@link guldilin.proxy.api.dto.CityDTO#getPopulationDensity()}
     */
    @Parameter(
            names = {"-population-density"},
            description = "City population density")
    private Integer populationDensity;

    /**
     * City carCode.
     * {@link guldilin.proxy.api.dto.CityDTO#getCarCode()}
     */
    @Parameter(
            names = {"-car-code"},
            description = "City car code number")
    private Integer carCode;

    /**
     * Convert arguments to DTO for service usage.
     *
     * @return CityCreateUpdateDTO instance
     */
    public CityCreateUpdateDTO toDTO() {
        var dto = new CityCreateUpdateDTO();
        dto.setName(this.name);
        dto.setArea(this.area);
        dto.setPopulation(this.population);
        dto.setMetersAboveSeaLevel(this.metersAboveSeaLevel);
        dto.setPopulationDensity(this.populationDensity);
        dto.setCarCode(this.carCode);
        return dto;
    }
}
