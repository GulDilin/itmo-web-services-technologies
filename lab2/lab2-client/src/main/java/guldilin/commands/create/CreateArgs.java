package guldilin.commands.create;

import com.beust.jcommander.Parameter;
import guldilin.commands.common.Args;
import guldilin.proxy.api.CityCreateUpdateDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateArgs extends Args {
    @Parameter(
            names = {"-name"},
            required = true,
            description = "City name")
    private String name;

    @Parameter(
            names = {"-area"},
            required = true,
            description = "City area number")
    private Integer area;

    @Parameter(
            names = {"-population"},
            required = true,
            description = "City population value")
    private Integer population;

    @Parameter(
            names = {"-meters-above-sea-level"},
            required = true,
            description = "Meters above sea level for City")
    private Float metersAboveSeaLevel;

    @Parameter(
            names = {"-population-density"},
            required = true,
            description = "City population density")
    private Integer populationDensity;

    @Parameter(
            names = {"-car-code"},
            required = true,
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
