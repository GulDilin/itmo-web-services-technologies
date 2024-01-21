//package guldilin.cli;
//
//import com.beust.jcommander.JCommander;
//import guldilin.RestApplication;
//import guldilin.config.PropertyKey;
//import jakarta.enterprise.inject.se.SeContainer;
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//import jakarta.ws.rs.core.Application;
//import org.glassfish.grizzly.http.server.HttpServer;
//import org.glassfish.grizzly.http.server.StaticHttpHandler;
//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.jboss.weld.environment.se.Weld;
//import org.jboss.weld.environment.se.WeldContainer;
//
///**
// * CLI wrapper for standalone server run (without application server).
// */
//public final class Standalone {
//    /**
//     * Default empty constructor.
//     */
//    private Standalone() {
//        // empty constructor
//    }
//
//    /**
//     * @param args Parsed arguments
//     * @return properties
//     */
//    public static Map<PropertyKey, String> generateParams(final Args args) {
//        Map<PropertyKey, String> params = new HashMap<>();
//        params.put(PropertyKey.APP_HOST, args.getHost());
//        params.put(PropertyKey.APP_PORT, args.getPort().toString());
//        String url = String.format("%s:%s", params.get(PropertyKey.APP_HOST), params.get(PropertyKey.APP_PORT));
//        try {
//            new URL(url).toURI();
//        } catch (MalformedURLException | URISyntaxException e) {
//            throw new IllegalArgumentException("Incorrect URL format for " + url);
//        }
//        params.put(PropertyKey.APP_URL, url);
//        params.put(PropertyKey.DB_HOST, args.getDbHost());
//        params.put(PropertyKey.DB_PORT, args.getDbPort());
//        params.put(PropertyKey.DB_NAME, args.getDbName());
//        params.put(PropertyKey.DB_USERNAME, args.getDbUsername());
//        params.put(PropertyKey.DB_PASSWORD, args.getDbPassword());
//
//        params.forEach((key, value) -> System.setProperty(key.name(), value));
//        return params;
//    }
//
//    /**
//     * Publish endpoints.
//     *
//     * @param container CDI container
//     * @param params    property-value params map
//     */
//    public static void publish(final Weld initializer, final SeContainer container, final Map<PropertyKey, String> params) {
//        try {
//            String baseUrl = params.get(PropertyKey.APP_URL);
//            if (!baseUrl.endsWith("/")) baseUrl += "/";
//            System.out.println("Start server on address " + baseUrl);
//
//            Application application = container.select(RestApplication.class).get();
////            ResourceConfig.forApplication(application)
//            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUrl), new ResourceConfig(application.getClasses()));
//            server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("openapi.json"), "/api/openapi.json");
//            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//                server.shutdownNow();
//                initializer.shutdown();
//            }));
//            server.start();
//            Thread.currentThread().join();
//        } catch (Exception e) {
//            System.err.println("Error during server start. Reason: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Main method for standalone app.
//     *
//     * @param argv CLI args
//     */
//    public static void main(final String[] argv) {
//        Args args = new Args();
//        JCommander commander = JCommander.newBuilder()
//                .programName("lab2-server.jar")
//                .addObject(args)
//                .build();
//        try {
//            commander.parse(argv);
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//            return;
//        }
//        if (args.getHelp()) {
//            commander.usage();
//            return;
//        }
//        Weld initializer = new Weld();
//        WeldContainer container = initializer.initialize();
//        publish(initializer, container, generateParams(args));
//
//        System.out.println("Server endpoints published");
//    }
//}
