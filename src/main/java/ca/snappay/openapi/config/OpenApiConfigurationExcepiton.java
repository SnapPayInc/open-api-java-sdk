package ca.snappay.openapi.config;

/**
 * This exception is thrown when something is wrong with configuration.
 *
 * @author shawndu
 * @version 1.0
 */
public class OpenApiConfigurationExcepiton extends RuntimeException {

    public OpenApiConfigurationExcepiton() {
    }

    public OpenApiConfigurationExcepiton(String message) {
        super(message);
    }

    public OpenApiConfigurationExcepiton(Throwable cause) {
        super(cause);
    }

    public OpenApiConfigurationExcepiton(String message, Throwable cause) {
        super(message, cause);
    }

}
