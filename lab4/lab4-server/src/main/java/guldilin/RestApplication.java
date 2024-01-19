package guldilin;

import guldilin.controller.CityResource;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {
    private static final Logger LOGGER = LogManager.getLogger(RestApplication.class);

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classSet = new HashSet<>();
        classSet.add( CityResource.class );
        return classSet;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("init RestApplication");
    }
}
