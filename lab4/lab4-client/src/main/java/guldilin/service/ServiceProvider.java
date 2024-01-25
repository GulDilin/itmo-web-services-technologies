package guldilin.service;

import guldilin.proxy.api.CityApi;

/**
 * Interface for service provider.
 */
public interface ServiceProvider {
    /**
     * Provides City service of proxy-api for server.
     *
     * @return City service instance
     */
    CityApi provideCityService();
}
