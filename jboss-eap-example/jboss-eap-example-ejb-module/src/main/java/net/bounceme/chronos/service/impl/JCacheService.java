package net.bounceme.chronos.service.impl;

import java.util.ServiceLoader;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import lombok.extern.jbosslog.JBossLog;

@Singleton
@Startup
@JBossLog
public class JCacheService {
    
    private Cache<String, String> cache;
    
    @PostConstruct
    public void init() {
        try {
        	// Usar ServiceLoader para encontrar proveedores disponibles
            ServiceLoader<CachingProvider> serviceLoader = 
                ServiceLoader.load(CachingProvider.class);
            
            CachingProvider provider = null;
            for (CachingProvider p : serviceLoader) {
                log.info("🔍 Proveedor JCache encontrado: " + p.getClass().getName());
                if (p.getClass().getName().contains("infinispan")) {
                    provider = p;
                    break;
                }
            }
            
            if (provider == null && serviceLoader.iterator().hasNext()) {
                provider = serviceLoader.iterator().next();
            }
            
            if (provider == null) {
                log.error("❌ No se encontró ningún proveedor JCache");
                return;
            }
            
            log.info("✅ Usando proveedor: " + provider.getClass().getName());
            
            CacheManager cacheManager = provider.getCacheManager(
                getClass().getResource("/META-INF/infinispan.xml").toURI(),
                getClass().getClassLoader()
            );
            
            cache = cacheManager.getCache("default", String.class, String.class);
            if (cache == null) {
                MutableConfiguration<String, String> config = new MutableConfiguration<>();
                config.setTypes(String.class, String.class)
                      .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
                cache = cacheManager.createCache("default", config);
            }
            
            log.infof("Caché cargada correctamente: %s", cache.toString());
        } catch (Exception e) {
            log.error("Error al cargar cache:", e);
        }
    }
}