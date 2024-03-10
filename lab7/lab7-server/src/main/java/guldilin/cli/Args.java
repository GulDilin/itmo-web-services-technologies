package guldilin.cli;

import com.beust.jcommander.Parameter;
import lombok.Data;

/**
 * Arguments class for commands.
 */
@Data
public class Args {
    /**
     * Default server port.
     */
    public static final int DEFAULT_PORT = 8080;

    /**
     * Help argument. If set - help message will be shown and command won't be executed.
     */
    @Parameter(
            names = {"-h", "-help"},
            help = true)
    private Boolean help = false;

    /**
     * Server host.
     */
    @Parameter(
            names = {"-host"},
            description = "Server URL base")
    private String host = "http://localhost";

    /**
     * Server port.
     */
    @Parameter(
            names = {"-port"},
            description = "Server URL port")
    private Integer port = Args.DEFAULT_PORT;

    /**
     * Database host.
     */
    @Parameter(
            names = {"-db_host"},
            required = true,
            description = "Database host (example: localhost)")
    private String dbHost;

    /**
     * Database port.
     */
    @Parameter(
            names = {"-db_port"},
            required = true,
            description = "Database port (example: 5432)")
    private String dbPort;

    /**
     * Database name.
     */
    @Parameter(
            names = {"-db_name"},
            required = true,
            description = "Database name (example: tws_db)")
    private String dbName;

    /**
     * Database username.
     */
    @Parameter(
            names = {"-db_username"},
            required = true,
            description = "Database username")
    private String dbUsername;

    /**
     * Database password.
     */
    @Parameter(
            names = {"-db_password"},
            required = true,
            description = "Database password")
    private String dbPassword;

    /**
     * Database username.
     */
    @Parameter(
            names = {"-uddi_username"},
            required = true,
            description = "UDDI service discovery username")
    private String uddiUsername;

    /**
     * Database password.
     */
    @Parameter(
            names = {"-uddi_password"},
            required = true,
            description = "UDDI service discovery password")
    private String uddiPassword;
}
