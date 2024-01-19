package guldilin.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;
import java.util.Objects;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("")
public class StaticResource {
    @Inject
    ServletContext context;

    @GET
    @Path("/openapi.json")
    public Response staticResources() {
        InputStream resource = context.getResourceAsStream("openapi.json");

        return Objects.isNull(resource)
                ? Response.status(NOT_FOUND).build()
                : Response.ok().entity(resource).build();
    }
}
