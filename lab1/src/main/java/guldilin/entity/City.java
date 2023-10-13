package guldilin.entity;

import guldilin.dto.CityDTO;
import guldilin.exceptions.ErrorMessages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "city")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class City extends AbstractEntity {
    @Column(name = "name", nullable = false)
    @NotBlank(message = ErrorMessages.NOT_BLANK)
    @FilterableField
    private String name;

    @Column(name = "area", nullable = false)
    @NotNull(message = ErrorMessages.NOT_NULL)
    @Min(value = 0, message = ErrorMessages.MIN_0)
    @FilterableField
    private Integer area;

    @Column(name = "population", nullable = false)
    @NotNull(message = ErrorMessages.NOT_NULL)
    @Min(value = 0, message = ErrorMessages.MIN_0)
    @FilterableField
    private Integer population;

    @Column(name = "meters_above_sea_level")
    @FilterableField
    private Float metersAboveSeaLevel;

    @Column(name = "population_density")
    @Min(value = 0, message = ErrorMessages.MIN_0)
    @FilterableField
    private Integer populationDensity;

    @Column(name = "car_code")
    @Min(value = 0, message = ErrorMessages.MIN_0)
    @Max(value = 1000, message = ErrorMessages.MAX_1000)
    @FilterableField
    private Integer carCode;

    @Override
    public CityDTO mapToDTO() {
        return new CityDTO(this);
    }
}
