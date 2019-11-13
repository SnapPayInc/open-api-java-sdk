package ca.snappay.openapi.config;

/**
 * Implementations of this interface is capable to resolve configuration from certain source.
 *
 * @author shawndu
 * @version 1.0
 */
public interface ConfigurationProvider {

    /**
     * Resolves the configuration from certain source.
     *
     * @return the configuration.
     */
    ConfigurationHolder resolveConfiguration();

}
