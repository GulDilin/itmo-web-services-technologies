package guldilin.cli;

import com.beust.jcommander.Parameter;
import lombok.Data;

/**
 * Arguments class for commands.
 */
@Data
public class Args {
    public static final int DEFAULT_PORT = 8080;

    @Parameter(
            names = {"-h", "-help"},
            help = true)
    private Boolean help = false;

    @Parameter(
            names = {"-host"},
            description = "Server URL base")
    private String host = "http://localhost";

    @Parameter(
            names = {"-port"},
            description = "Server URL port")
    private Integer port = Args.DEFAULT_PORT;

    @Parameter(
            names = {"-db_host"},
            required = true,
            description = "Database host (example: localhost)")
    private String dbHost;

    @Parameter(
            names = {"-db_port"},
            required = true,
            description = "Database port (example: 5432)")
    private String dbPort;

    @Parameter(
            names = {"-db_name"},
            required = true,
            description = "Database name (example: tws_db)")
    private String dbName;

    @Parameter(
            names = {"-db_username"},
            required = true,
            description = "Database username")
    private String dbUsername;

    @Parameter(
            names = {"-db_password"},
            required = true,
            description = "Database password")
    private String dbPassword;
}
