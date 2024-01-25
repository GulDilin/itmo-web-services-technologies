package guldilin.commands.common;

import guldilin.proxy.api.dto.AbstractEntityDTO;
import guldilin.proxy.api.dto.CityDTO;
import guldilin.proxy.api.dto.PaginationDTO;
import java.io.PrintStream;
import java.util.stream.Collectors;

/**
 * Class that helps with printing entities to string.
 */
public final class EntitiesPrinter {

    /**
     * Empty Constructor for utility class.
     */
    private EntitiesPrinter() {
        // empty
    }

    /**
     * Convert AbstractEntityDTO to string.
     *
     * @param entityDTO dto object
     * @return string for printing
     */
    public static String getStringValue(final AbstractEntityDTO entityDTO) {
        if (entityDTO instanceof CityDTO) {
            return getStringValue((CityDTO) entityDTO);
        }
        return String.format(
                """
                            Entity:
                            - id:           %d
                            - createdAt:    %s
                            - updatedAt:    %s
                        """,
                entityDTO.getId(), entityDTO.getCreationAt(), entityDTO.getUpdatedAt());
    }

    /**
     * Convert CityDTO to string.
     *
     * @param cityDTO dto object
     * @return string for printing
     */
    public static String getStringValue(final CityDTO cityDTO) {
        return String.format(
                """
                            City:
                            - id:                   %d
                            - name:                 %s
                            - area:                 %d
                            - carCode:              %d
                            - metersAboveSeaLevel:  %f
                            - population:           %d meters
                            - populationDensity:    %d people/km^2
                            - createdAt:            %s
                            - updatedAt:            %s
                        """,
                cityDTO.getId(),
                cityDTO.getName(),
                cityDTO.getArea(),
                cityDTO.getCarCode(),
                cityDTO.getMetersAboveSeaLevel(),
                cityDTO.getPopulation(),
                cityDTO.getPopulationDensity(),
                cityDTO.getCreationAt(),
                cityDTO.getUpdatedAt());
    }

    /**
     * Convert PaginationDTO to string.
     *
     * @param paginationDTO dto object
     * @return string for printing
     */
    public static String getStringValue(final PaginationDTO<?> paginationDTO) {
        final int separatorLen = 40;
        String separator = "=".repeat(separatorLen) + "\n";
        return String.format(
                """
                        Result:
                        - total:        %d
                        - nextOffset:   %d
                        - items:
                        %s
                         """,
                paginationDTO.getTotal(),
                paginationDTO.getNextOffset(),
                paginationDTO.getItems().stream()
                        .map(EntitiesPrinter::getStringValue)
                        .collect(Collectors.joining(separator)));
    }

    /**
     * Print dto to printStream.
     *
     * @param printStream target print stream
     * @param paginationDTO pagination dto object
     */
    public static void print(final PrintStream printStream, final PaginationDTO<?> paginationDTO) {
        printStream.print(getStringValue(paginationDTO));
    }

    /**
     * Print dto to printStream.
     *
     * @param printStream target print stream
     * @param entityDTO entity dto object
     */
    public static void print(final PrintStream printStream, final AbstractEntityDTO entityDTO) {
        printStream.print(getStringValue(entityDTO));
    }
}
