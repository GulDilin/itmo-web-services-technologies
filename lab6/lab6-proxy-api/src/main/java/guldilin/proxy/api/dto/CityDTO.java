package guldilin.proxy.api.dto;

import java.io.Serializable;
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
public class CityDTO extends AbstractEntityDTO implements Serializable {
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
}
