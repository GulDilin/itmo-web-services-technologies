package guldilin;

import guldilin.controller.CityResource;
import guldilin.exceptions.mapper.EntryNotFoundMapper;
import guldilin.exceptions.mapper.FieldIsNotFilterableMapper;
import guldilin.exceptions.mapper.NotFoundMapper;
import guldilin.exceptions.mapper.ThrowableMapper;
import guldilin.exceptions.mapper.ValidationMapper;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationPath("/")
@OpenAPIDefinition(servers = @Server(url = "/lab4-server"))
public class RestApplication extends Application {
    private static final Logger LOGGER = LogManager.getLogger(RestApplication.class);

    @Override
    public Set<Class<?>> getClasses() {
        return Stream.of(
                        CityResource.class,
                        OpenApiResource.class,
                        ThrowableMapper.class,
                        EntryNotFoundMapper.class,
                        FieldIsNotFilterableMapper.class,
                        NotFoundMapper.class,
                        ValidationMapper.class)
                .collect(Collectors.toSet());
    }

    @PostConstruct
    public void init() {
        LOGGER.info("init RestApplication");
    }
}
