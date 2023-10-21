package guldilin.commands.common;

import guldilin.service.interfaces.AbstractEntityDTO;
import guldilin.service.interfaces.CityDTO;
import guldilin.service.interfaces.PaginationDTO;
import java.io.PrintStream;
import java.util.stream.Collectors;

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
                            - id:                   %d
                            - created:              %s
                            - updated:              %s
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
                            - car code:             %d
                            - above sea level:      %f meters
                            - population:           %d meters
                            - population density:   %d people/km^2
                            - created:              %s
                            - updated:              %s
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
    public static String getStringValue(final PaginationDTO paginationDTO) {
        final int separatorLen = 40;
        String separator = "=".repeat(separatorLen) + "\n";
        return String.format(
                """
                        Result:
                        - total:         %d
                        - next offset:   %d
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
    public static void print(final PrintStream printStream, final PaginationDTO paginationDTO) {
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
