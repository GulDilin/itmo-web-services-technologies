package guldilin.commands.find;

import com.beust.jcommander.Parameter;
import guldilin.commands.common.Args;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Args class for find command.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FindArgs extends Args {
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
    private Integer metersAboveSeaLevel;

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
     * Items limit.
     */
    @Parameter(
            names = {"-limit"},
            description = "Results limit")
    private Integer limit;

    /**
     * Items offset.
     */
    @Parameter(
            names = {"-offset"},
            description = "Results offset")
    private Integer offset;
}
