package guldilin.cli;

import com.beust.jcommander.JCommander;
import guldilin.config.FlywayMigrator;
import guldilin.config.PropertyKey;
import guldilin.service.interfaces.CityService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.xml.ws.Endpoint;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * CLI wrapper for standalone server run (without application server).
 */
public final class Standalone {
    /**
     * Default empty constructor.
     */
    private Standalone() {
        // empty constructor
    }

    /**
     * @param args Parsed arguments
     * @return properties
     */
    public static Map<PropertyKey, String> generateParams(final Args args) {
        Map<PropertyKey, String> params = new HashMap<>();
        params.put(PropertyKey.APP_HOST, args.getHost());
        params.put(PropertyKey.APP_PORT, args.getPort().toString());
        String url = String.format("%s:%s", params.get(PropertyKey.APP_HOST), params.get(PropertyKey.APP_PORT));
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException("Incorrect URL format for " + url);
        }
        params.put(PropertyKey.APP_URL, url);
        params.put(PropertyKey.DB_HOST, args.getDbHost());
        params.put(PropertyKey.DB_PORT, args.getDbPort());
        params.put(PropertyKey.DB_NAME, args.getDbName());
        params.put(PropertyKey.DB_USERNAME, args.getDbUsername());
        params.put(PropertyKey.DB_PASSWORD, args.getDbPassword());

        params.put(PropertyKey.UDDI_HOST, args.getUddiServerName());
        params.put(PropertyKey.UDDI_PORT, args.getUddiPort().toString());
        params.put(PropertyKey.UDDI_USERNAME, args.getUddiUsername());
        params.put(PropertyKey.UDDI_PASSWORD, args.getUddiPassword());

        params.forEach((key, value) -> System.setProperty(key.name(), value));
        return params;
    }

    /**
     * Publish endpoints.
     *
     * @param container CDI container
     * @param params    property-value params map
     */
    public static void publish(final SeContainer container, final Map<PropertyKey, String> params) {
        try {
            String baseUrl = params.get(PropertyKey.APP_URL);
            System.out.println("Start server on address " + baseUrl);
            String urlCityService = String.valueOf(new URL(String.format("%s/CityService", baseUrl)));
            // Get bean from CDI container to invoke startup migrations
            container.select(FlywayMigrator.class).get();
            CityService cityService = container.select(CityService.class).get();
            Endpoint.publish(urlCityService, cityService);
        } catch (Exception e) {
            System.err.println("Error during server start. Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main method for standalone app.
     *
     * @param argv CLI args
     */
    public static void main(final String[] argv) {
        Args args = new Args();
        JCommander commander = JCommander.newBuilder()
                .programName("lab7-server.jar")
                .addObject(args)
                .build();
        try {
            commander.parse(argv);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        if (args.getHelp()) {
            commander.usage();
            return;
        }
        Weld initializer = new Weld();
        WeldContainer container = initializer.initialize();
        publish(container, generateParams(args));
        System.out.println("Server endpoints published");
    }
}
