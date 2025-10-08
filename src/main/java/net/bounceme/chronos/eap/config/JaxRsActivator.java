package net.bounceme.chronos.eap.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
    // Esta clase activa JAX-RS y establece el path base /rest
}