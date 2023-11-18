package guldilin.dto;

import guldilin.entity.City;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CityCreateDTO {
    private String name;
    private Integer area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private Integer populationDensity;
    private Integer carCode;

    /**
     * Constructor for DTO object from Entity.
     *
     * @param city JPA entity.
     */
    public CityCreateDTO(final City city) {
        this.name = city.getName();
        this.area = city.getArea();
        this.population = city.getPopulation();
        this.metersAboveSeaLevel = city.getMetersAboveSeaLevel();
        this.populationDensity = city.getPopulationDensity();
        this.carCode = city.getCarCode();
    }
}
