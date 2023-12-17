package guldilin.commands.patch;

import com.beust.jcommander.Parameter;
import guldilin.commands.common.Args;
import guldilin.proxy.api.CityCreateUpdateDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PatchArgs extends Args {
    @Parameter(
            names = {"-id"},
            description = "City id")
    private Integer id;

    @Parameter(
            names = {"-name"},
            description = "City name")
    private String name;

    @Parameter(
            names = {"-area"},
            description = "City area number")
    private Integer area;

    @Parameter(
            names = {"-population"},
            description = "City population value")
    private Integer population;

    @Parameter(
            names = {"-meters-above-sea-level"},
            description = "Meters above sea level for City")
    private Float metersAboveSeaLevel;

    @Parameter(
            names = {"-population-density"},
            description = "City population density")
    private Integer populationDensity;

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
