package guldilin;

import guldilin.controller.CityResource;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ApplicationPath("/api")
public class RestApplication extends Application {
    private static final Logger LOGGER = LogManager.getLogger(RestApplication.class);

    @Override
    public Set<Class<?>> getClasses() {
//        return Stream.of(CityResource.class, OpenApiResource.class).collect(Collectors.toSet());
        return Stream.of(CityResource.class).collect(Collectors.toSet());
    }

    @PostConstruct
    public void init() {
        LOGGER.info("init RestApplication");
    }
}
