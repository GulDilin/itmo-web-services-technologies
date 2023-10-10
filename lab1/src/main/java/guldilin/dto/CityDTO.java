package guldilin.dto;

import guldilin.entity.City;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO extends AbstractEntityDTO {
    private String name;
    private Integer area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private Integer populationDensity;
    private Integer carCode;

    public CityDTO(City city) {
        super(city);
        this.name = city.getName();
        this.area = city.getArea();
        this.population = city.getPopulation();
        this.metersAboveSeaLevel = city.getMetersAboveSeaLevel();
        this.populationDensity = city.getPopulationDensity();
        this.carCode = city.getCarCode();
    }
}
