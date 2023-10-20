package guldilin;

import guldilin.service.impl.CityService;
import guldilin.service.impl.CityService_Service;
import guldilin.service.impl.FieldIsNotFilterable;
import guldilin.service.interfaces.PaginationDTO;
import java.util.Collections;

public final class Main {

    /**
     * Empty Constructor for Utility class.
     */
    private Main() {
        // empty
    }
    /**
     * Main.
     *
     * @param args cli args
     */
    public static void main(final String[] args) throws FieldIsNotFilterable {
        CityService_Service service = new CityService_Service();
        CityService cityService = service.getCityPort();
        PaginationDTO cityResults = cityService.findByFilter(Collections.emptyList(), null);
        System.out.println(cityResults);
        cityResults.getItems().forEach(System.out::println);
    }
}
