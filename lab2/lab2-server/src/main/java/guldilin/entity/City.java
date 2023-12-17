package guldilin.entity;

import guldilin.dto.CityDTO;
import guldilin.exceptions.ErrorMessages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * City entity.
 */
@Entity(name = "city")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public final class City extends AbstractEntity {
    /**
     * Max value for CAR CODE.
     */
    public static final long MAX_CAR_CODE = 1000L;

    /**
     * City name.
     */
    @FilterableField
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = ErrorMessages.NOT_BLANK)
    private String name;

    /**
     * City area number.
     */
    @Column(name = "area", nullable = false)
    @NotNull(message = ErrorMessages.NOT_NULL)
    @Min(value = 0, message = ErrorMessages.MIN_0)
    @FilterableField
    private Integer area;

    /**
     * City population.
     */
    @Column(name = "population", nullable = false)
    @NotNull(message = ErrorMessages.NOT_NULL)
    @Min(value = 0, message = ErrorMessages.MIN_0)
    @FilterableField
    private Integer population;

    /**
     * Meters above sea level.
     */
    @Column(name = "meters_above_sea_level")
    @FilterableField
    private Float metersAboveSeaLevel;

    /**
     * Population density. people / meters ^ 2.
     */
    @Column(name = "population_density")
    @Min(value = 0, message = ErrorMessages.MIN_0)
    @FilterableField
    private Integer populationDensity;

    /**
     * City car code.
     */
    @Column(name = "car_code")
    @Min(value = 0, message = ErrorMessages.MIN_0)
    @Max(value = City.MAX_CAR_CODE, message = ErrorMessages.MAX_1000)
    @FilterableField
    private Integer carCode;

    /**
     * {@inheritDoc}
     */
    @Override
    public CityDTO mapToDTO() {
        return new CityDTO(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        City city = (City) o;
        return Objects.equals(name, city.name)
                && Objects.equals(area, city.area)
                && Objects.equals(population, city.population)
                && Objects.equals(metersAboveSeaLevel, city.metersAboveSeaLevel)
                && Objects.equals(populationDensity, city.populationDensity)
                && Objects.equals(carCode, city.carCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, area, population, metersAboveSeaLevel, populationDensity, carCode);
    }
}
