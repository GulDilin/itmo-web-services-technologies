package guldilin.cli;

import com.beust.jcommander.JCommander;
import guldilin.RestApplication;
import guldilin.config.PropertyKey;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

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
    //    /**
    //     * Just logger.
    //     */
    //    private static final Logger LOGGER = LogManager.getLogger(Standalone.class);

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

        params.forEach((key, value) -> System.setProperty(key.name(), value));
        return params;
    }

    public static void runUndertow(final Map<PropertyKey, String> params) {
        UndertowJaxrsServer server = new UndertowJaxrsServer();

        ResteasyDeployment deployment = new ResteasyDeploymentImpl();
        deployment.setApplicationClass(RestApplication.class.getName());
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");
        deployment.getProviderClasses().add(ResteasyJackson2Provider.class.getName());

        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/");
        deploymentInfo.setClassLoader(RestApplication.class.getClassLoader())
                .setContextPath("/lab4-server")
                .setDeploymentName("Rest Application")
                .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

        server.deploy(deploymentInfo);

        Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(
                Integer.parseInt(params.get(PropertyKey.APP_PORT)),
                "0.0.0.0"
        );
        server.start(serverBuilder);
    }

    /**
     * Main method for standalone app.
     *
     * @param argv CLI args
     */
    public static void main(final String[] argv) throws InterruptedException {
        Args args = new Args();
        JCommander commander = JCommander.newBuilder()
                .programName("lab2-server.jar")
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

        runUndertow(generateParams(args));

        System.out.println("Server endpoints published");
    }
}
