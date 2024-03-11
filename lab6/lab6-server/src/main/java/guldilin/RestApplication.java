package guldilin;

import guldilin.controller.CityResource;
import guldilin.exceptions.mapper.EntryNotFoundMapper;
import guldilin.exceptions.mapper.FieldIsNotFilterableMapper;
import guldilin.exceptions.mapper.NotAllowedMapper;
import guldilin.exceptions.mapper.ThrowableMapper;
import guldilin.exceptions.mapper.ValidationMapper;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Rest application configuration.
 */
@ApplicationPath("/api")
@OpenAPIDefinition(servers = @Server(url = "/lab6-server"))
public class RestApplication extends Application {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(RestApplication.class);

    /**
     * Provide services and mappers classes for app.
     * @return classes set.
     */
    @Override
    public Set<Class<?>> getClasses() {
        return Stream.of(
                        CityResource.class,
                        OpenApiResource.class,
                        ThrowableMapper.class,
                        NotAllowedMapper.class,
                        EntryNotFoundMapper.class,
                        FieldIsNotFilterableMapper.class,
                        ValidationMapper.class)
                .collect(Collectors.toSet());
    }

    /**
     * Post init.
     */
    @PostConstruct
    public void init() {
        LOGGER.info("init RestApplication");
    }
}
