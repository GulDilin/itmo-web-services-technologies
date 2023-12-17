package guldilin.service;

import guldilin.proxy.api.CityWs;

/**
 * Interface for service provider.
 */
public interface ServiceProvider {
    /**
     * Provides City service of proxy-api for server.
     *
     * @return City service instance
     */
    CityWs provideCityService();
}
