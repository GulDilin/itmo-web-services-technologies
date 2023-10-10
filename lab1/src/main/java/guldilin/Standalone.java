package guldilin;

import guldilin.config.PropertyKey;
import guldilin.service.impl.CityServiceImpl;
import jakarta.xml.ws.Endpoint;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.cli.*;

public final class Standalone {

    private Standalone() {
        // empty constructor
    }

    public static Options defineCliOptions() {
        Options options = new Options();
        options.addOption("help", false, "Show help message");
        options.addOption(PropertyKey.APP_HOST.getCmdAlias(), true, PropertyKey.APP_HOST.getDescription());
        options.addOption(PropertyKey.APP_PORT.getCmdAlias(), true, PropertyKey.APP_PORT.getDescription());
        return options;
    }

    public static String getOptionOrDefault(CommandLine line, PropertyKey propertyKey) {

        return Optional.ofNullable(line.getOptionValue(propertyKey.getCmdAlias()))
                .orElse(propertyKey.getDefaultValue());
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
        return params;
    }

    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("server", defineCliOptions());
    }

    public static void publish(Map<PropertyKey, String> params) {
        try {
            String baseUrl = params.get(PropertyKey.APP_URL);
            System.out.println("Start server on address " + baseUrl);
            String urlCityService = String.valueOf(new URL(String.format("%s/CityService", baseUrl)));
            Endpoint.publish(urlCityService, new CityServiceImpl());
        } catch (Exception e) {
            System.err.println("Error during server start. Reason: " + e.getMessage());
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
            publish(validateOptions(line));
        } catch (ParseException exp) {
            System.err.println("Parsing failed. Reason: " + exp.getMessage());
            printHelp();
        }
    }
}
