package guldilin.entity;

import guldilin.dto.CityDTO;
import guldilin.exceptions.ErrorMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

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
    private String name;

    @Column(name = "area", nullable = false)
    @NotNull(message = ErrorMessages.NOT_NULL)
    @Min(value = 0, message = ErrorMessages.MIN_0)
    private Integer area;

    @Column(name = "population", nullable = false)
    @NotNull(message = ErrorMessages.NOT_NULL)
    @Min(value = 0, message = ErrorMessages.MIN_0)
    private Integer population;

    @Column(name = "meters_above_sea_level")
    private Float metersAboveSeaLevel;

    @Column(name = "population_density")
    @Min(value = 0, message = ErrorMessages.MIN_0)
    private Integer populationDensity;

    @Column(name = "car_code")
    @Min(value = 0, message = ErrorMessages.MIN_0)
    @Max(value = 1000, message = ErrorMessages.MAX_1000)
    private Integer carCode;

    @Override
    public CityDTO mapToDTO() {
        return new CityDTO(this);
    }
}
