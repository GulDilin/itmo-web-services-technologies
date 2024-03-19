package guldilin.cli;

import com.beust.jcommander.JCommander;
import guldilin.RestApplication;
import guldilin.config.PropertyKey;
import io.undertow.Undertow;
import io.undertow.server.handlers.cache.DirectBufferCache;
import io.undertow.server.handlers.resource.CachingResourceManager;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
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

    /**
     * Just logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(Standalone.class);

    /**
     * @param args Parsed arguments
     * @return properties
     */
    public static Map<PropertyKey, String> generateParams(final Args args) {
        Map<PropertyKey, String> params = new HashMap<>();
        params.put(PropertyKey.APP_HOST, args.getHost());
        params.put(PropertyKey.APP_PORT, args.getPort().toString());
        String url = String.format("%s:%s", params.get(PropertyKey.APP_HOST), params.get(PropertyKey.APP_PORT));

        params.put(PropertyKey.APP_URL, url);
        params.put(PropertyKey.DB_HOST, args.getDbHost());
        params.put(PropertyKey.DB_PORT, args.getDbPort());
        params.put(PropertyKey.DB_NAME, args.getDbName());
        params.put(PropertyKey.DB_USERNAME, args.getDbUsername());
        params.put(PropertyKey.DB_PASSWORD, args.getDbPassword());

        params.forEach((key, value) -> System.setProperty(key.name(), value));
        return params;
    }

    private static ResourceHandler createStaticResourceHandler() {
        final ResourceManager staticResources =
                new ClassPathResourceManager(RestApplication.class.getClassLoader(), "static");
        // Cache tuning is copied from Undertow unit tests.
        final var cache = new DirectBufferCache(1024, 10, 10480);
        final ResourceManager cachedResources = new CachingResourceManager(
                100, 65536, cache, staticResources, (int) Duration.ofDays(1).getSeconds());
        final ResourceHandler resourceHandler = new ResourceHandler(cachedResources);
        resourceHandler.setWelcomeFiles("index.html");
        return resourceHandler;
    }

    private static ResteasyDeployment createDeployment() {
        ResteasyDeployment deployment = new ResteasyDeploymentImpl();
        deployment.setApplicationClass(RestApplication.class.getName());
        deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());
        // register json provider
        deployment.getProviderClasses().add(ResteasyJackson2Provider.class.getName());
        return deployment;
    }

    private static DeploymentInfo createDeploymentInfo(
            final UndertowJaxrsServer server, final ResteasyDeployment deployment) {
        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/");
        deploymentInfo
                .setClassLoader(RestApplication.class.getClassLoader())
                .setContextPath("/lab6-auth-server/api")
                .setDeploymentName("Rest Application")
                .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));
        return deploymentInfo;
    }

    /**
     * Creates and start Undertow JAX-RS embedded server.
     * @param params startup parameters
     */
    public static void startServer(final Map<PropertyKey, String> params) {
        UndertowJaxrsServer server = new UndertowJaxrsServer();
        var deployment = createDeployment();
        var deploymentInfo = createDeploymentInfo(server, deployment);
        server.deploy(deploymentInfo);

        Undertow.Builder serverBuilder = Undertow.builder()
                .addHttpListener(Integer.parseInt(params.get(PropertyKey.APP_PORT)), params.get(PropertyKey.APP_HOST));
        server.start(serverBuilder);
        server.addResourcePrefixPath("/lab6-auth-server", createStaticResourceHandler());
    }

    /**
     * Main method for standalone app.
     *
     * @param argv CLI args
     */
    public static void main(final String[] argv) {
        Args args = new Args();
        JCommander commander = JCommander.newBuilder()
                .programName("lab6-auth-server.jar")
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

        startServer(generateParams(args));
        LOGGER.info("Server endpoints published");
    }
}
