package org.xdi.service.cache;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author yuriyz on 02/21/2017.
 */

public class InMemoryCacheProvider extends AbstractCacheProvider<ExpiringMap> {

    private static final Logger log = LoggerFactory.getLogger(MemcachedProvider.class);

    private ExpiringMap<String, Object> map = ExpiringMap.builder().build();

    private InMemoryConfiguration configuration;

    public InMemoryCacheProvider(InMemoryConfiguration configuration) {
        this.configuration = configuration;
    }

    public void create() {
        log.debug("Starting InMemoryCacheProvider ...");
        try {
            map = ExpiringMap.builder().build();

            log.debug("InMemoryCacheProvider started.");
        } catch (Exception e) {
            throw new IllegalStateException("Error starting InMemoryCacheProvider", e);
        }
    }

    public void destroy() {
        log.debug("Destroying InMemoryCacheProvider");

        map.clear();

        log.debug("Destroyed InMemoryCacheProvider");
    }

    @Override
    public ExpiringMap getDelegate() {
        return map;
    }

    @Override
    public Object get(String region, String key) {
        return map.get(key);
    }

    @Override // it is so weird but we use as workaround "region" field to pass "expiration" for put operation
    public void put(String expirationInSeconds, String key, Object object) {
        map.put(key, object, ExpirationPolicy.CREATED, putExpiration(expirationInSeconds), TimeUnit.SECONDS);
    }

    private int putExpiration(String expirationInSeconds) {
        try {
            return Integer.parseInt(expirationInSeconds);
        } catch (Exception e) {
            return configuration.getDefaultPutExpiration();
        }
    }

    @Override
    public void remove(String region, String key) {
        map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }
}
