package guldilin.dto;

import guldilin.entity.City;
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
     * Map DTO instance to City entity object.
     *
     * @return City object
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
     * Update entity with current data.
     *
     * @param entity entry to update
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
}
