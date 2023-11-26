package guldilin.service;

import guldilin.proxy.api.City;

public interface ServiceProvider {
    /**
     * Provides City service of proxy-api for server.
     *
     * @return City service instance
     */
    City provideCityService();
}
