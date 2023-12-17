package guldilin.dto;

import guldilin.entity.City;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CityCreateUpdateDTO implements MappableToEntity, UpdatableToEntity<City> {
    private String name;
    private Integer area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private Integer populationDensity;
    private Integer carCode;

    /**
     * {@inheritDoc}
     */
    @Override
    public City mapToEntity() {
        return City.builder()
                .name(name)
                .area(area)
                .population(population)
                .metersAboveSeaLevel(metersAboveSeaLevel)
                .populationDensity(populationDensity)
                .carCode(carCode)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEntity(final City entity) {
        entity.setName(name);
        entity.setArea(area);
        entity.setPopulation(population);
        entity.setMetersAboveSeaLevel(metersAboveSeaLevel);
        entity.setPopulationDensity(populationDensity);
        entity.setCarCode(carCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void patchEntity(City entity) {
        Optional.ofNullable(name).ifPresent(entity::setName);
        Optional.ofNullable(area).ifPresent(entity::setArea);
        Optional.ofNullable(population).ifPresent(entity::setPopulation);
        Optional.ofNullable(populationDensity).ifPresent(entity::setPopulationDensity);
        Optional.ofNullable(metersAboveSeaLevel).ifPresent(entity::setMetersAboveSeaLevel);
        Optional.ofNullable(carCode).ifPresent(entity::setCarCode);
    }
}
