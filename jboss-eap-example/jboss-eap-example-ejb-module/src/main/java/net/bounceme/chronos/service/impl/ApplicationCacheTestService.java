package net.bounceme.chronos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import lombok.extern.jbosslog.JBossLog;

@Singleton
@Startup
@JBossLog
public class ApplicationCacheTestService {

    @Resource(lookup = "java:jboss/infinispan/container/application")
    private EmbeddedCacheManager cacheManager;

    private Cache<String, Object> appCache;

    @PostConstruct
    public void init() {
        log.info("🚀 Inicializando ApplicationCacheTestService");
        appCache = cacheManager.getCache("local");
        
        // Cargar algunos datos de prueba
        cargarDatosDePrueba();
    }

    private void cargarDatosDePrueba() {
        appCache.put("config.app.nombre", "Mi Aplicación EAP 8");
        appCache.put("config.app.version", "2.4.0");
        appCache.put("config.app.modo", "producción");
        
        log.info("📦 Datos de prueba cargados en cache de aplicación");
    }

    public String obtenerConfiguracion(String clave) {
        long start = System.currentTimeMillis();
        Object valor = appCache.get(clave);
        long tiempo = System.currentTimeMillis() - start;
        
        log.infof("⏱️ [Cache App] Obteniendo clave '%s' - Tiempo: %d ms - Valor: %s", 
                 clave, tiempo, valor);
        
        return valor != null ? valor.toString() : null;
    }

    public void actualizarConfiguracion(String clave, String valor) {
        long start = System.currentTimeMillis();
        appCache.put(clave, valor);
        long tiempo = System.currentTimeMillis() - start;
        
        log.infof("⏱️ [Cache App] Actualizando clave '%s' - Tiempo: %d ms", clave, tiempo);
    }

    public Map<String, Object> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("tamano_cache", appCache.size());
        stats.put("claves", new ArrayList<>(appCache.keySet()));
        
        return stats;
    }

    public void limpiarCache() {
        appCache.clear();
        log.info("🧹 Cache de aplicación limpiada");
    }
}