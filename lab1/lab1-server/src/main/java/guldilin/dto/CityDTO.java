package guldilin.dto;

import guldilin.entity.City;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * City DTO class.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlType(name = "city")
@XmlRootElement
public class CityDTO extends AbstractEntityDTO {

    /**
     * City name.
     */
    private String name;
    /**
     * City area number.
     */
    private Integer area;
    /**
     * City population.
     */
    private Integer population;
    /**
     * Meters above sea level.
     */
    private Float metersAboveSeaLevel;

    /**
     * Population density. people / meters ^ 2.
     */
    private Integer populationDensity;
    /**
     * City car code.
     */
    private Integer carCode;

    /**
     * Constructor for DTO object from Entity.
     *
     * @param city JPA entity.
     */
    public CityDTO(final City city) {
        super(city);
        this.name = city.getName();
        this.area = city.getArea();
        this.population = city.getPopulation();
        this.metersAboveSeaLevel = city.getMetersAboveSeaLevel();
        this.populationDensity = city.getPopulationDensity();
        this.carCode = city.getCarCode();
    }
}
