package guldilin.dto;

import guldilin.entity.City;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * City DTO class.
 * {@link City}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class CityDTO extends AbstractEntityDTO {
    /**
     * DTO field shading an entity field with same name.
     */
    private String name;
    /**
     * DTO field shading an entity field with same name.
     */
    private Integer area;
    /**
     * DTO field shading an entity field with same name.
     */
    private Integer population;
    /**
     * DTO field shading an entity field with same name.
     */
    private Float metersAboveSeaLevel;
    /**
     * DTO field shading an entity field with same name.
     */
    private Integer populationDensity;
    /**
     * DTO field shading an entity field with same name.
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
