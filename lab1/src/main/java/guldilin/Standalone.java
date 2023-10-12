package guldilin;

import guldilin.config.PropertyKey;
import guldilin.repository.impl.SessionFactoryBuilderImpl;
import guldilin.repository.interfaces.TestRepo;
import guldilin.service.impl.CityServiceImpl;
import javax.xml.ws.Endpoint;

import guldilin.service.impl.TestServiceImpl;
import guldilin.service.interfaces.TestService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
//import org.jboss.weld.environment.se.Weld;
//import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Standalone {
    private final static List<PropertyKey> DB_OPTIONS = Stream.of(PropertyKey.DB_HOST,
            PropertyKey.DB_DIALECT,
            PropertyKey.DB_DRIVER,
            PropertyKey.DB_PROTOCOL,
            PropertyKey.DB_PORT,
            PropertyKey.DB_NAME,
            PropertyKey.DB_USERNAME,
            PropertyKey.DB_PASSWORD
    ).toList();

    private Standalone() {
        // empty constructor
    }

    public static Options defineCliOptions() {
        Options options = new Options();
        options.addOption("help", false, "Show help message");
        options.addOption(PropertyKey.APP_HOST.getCmdAlias(), true, PropertyKey.APP_HOST.getDescription());
        options.addOption(PropertyKey.APP_PORT.getCmdAlias(), true, PropertyKey.APP_PORT.getDescription());

        DB_OPTIONS.forEach(o-> options.addOption(o.getCmdAlias(), true, o.getDescription()));
        return options;
    }

    public static String getOptionOrDefault(CommandLine line, PropertyKey propertyKey) {

        return Optional.ofNullable(line.getOptionValue(propertyKey.getCmdAlias()))
                .orElse(propertyKey.getDefaultValue());
    }
    public static String getOptionValue(CommandLine line, PropertyKey propertyKey) {
        return line.getOptionValue(propertyKey.getCmdAlias());
    }

    public static Map<PropertyKey, String> validateOptions(CommandLine line) throws ParseException {
        Map<PropertyKey, String> params = new HashMap<>();
        params.put(PropertyKey.APP_HOST, getOptionOrDefault(line, PropertyKey.APP_HOST));
        params.put(PropertyKey.APP_PORT, getOptionOrDefault(line, PropertyKey.APP_PORT));
        String url = String.format("%s:%s", params.get(PropertyKey.APP_HOST), params.get(PropertyKey.APP_PORT));
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException("Incorrect URL format for " + url);
        }
        params.put(PropertyKey.APP_URL, url);

        DB_OPTIONS.stream()
                .map(o -> new AbstractMap.SimpleEntry<PropertyKey, String>(o, getOptionValue(line, o)))
                .filter(o -> Objects.nonNull(o.getValue()))
                .forEach(o -> System.setProperty(o.getKey().name(), o.getValue()));

        return params;
    }

    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("server", defineCliOptions());
    }

    public static void publish(SeContainer container, Map<PropertyKey, String> params) {
        try {
            String baseUrl = params.get(PropertyKey.APP_URL);
            System.out.println("Start server on address " + baseUrl);
            String urlCityService = String.valueOf(new URL(String.format("%s/CityService", baseUrl)));
//            new SessionFactoryBuilderImpl().getSessionFactory();
//            Endpoint.publish(urlCityService, new CityServiceImpl());
            TestService t = container.select(TestServiceImpl.class).get();
            Endpoint.publish(urlCityService, t);

        } catch (Exception e) {
            System.err.println("Error during server start. Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main method for standalone app.
     *
     * @param args CLI args
     */
    public static void main(final String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = defineCliOptions();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("help")) {
                printHelp();
                return;
            }
            SeContainerInitializer initializer = SeContainerInitializer.newInstance();
            SeContainer container = initializer.initialize();
            publish(container, validateOptions(line));
            System.out.println("Server endpoints published");
        } catch (ParseException exp) {
            System.err.println("Parsing failed. Reason: " + exp.getMessage());
            printHelp();
        }
    }
}
