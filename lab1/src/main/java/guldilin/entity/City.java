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
import org.hibernate.proxy.HibernateProxy;

@Entity(name = "city")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public final class City extends AbstractEntity {
    public static final Long MAX_CAR_CODE = 1000L;

    @FilterableField
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = ErrorMessages.NOT_BLANK)
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
    @Max(value = City.MAX_CAR_CODE, message = ErrorMessages.MAX_1000)
    @FilterableField
    private Integer carCode;

    /**
     * Create DTO object from this entity.
     * @return DTO object.
     */
    @Override
    public CityDTO mapToDTO() {
        return new CityDTO(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        assert o instanceof City;
        City city = (City) o;
        return getId() != null && Objects.equals(getId(), city.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
