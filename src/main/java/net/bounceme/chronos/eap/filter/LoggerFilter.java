package net.bounceme.chronos.eap.filter;

import java.io.IOException;

import org.jboss.logging.Logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class LoggerFilter implements Filter {
	
	// Crear el logger específico para esta clase
    private static final Logger logger = Logger.getLogger(LoggerFilter.class);
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        long startTime = System.currentTimeMillis();
        
        String clientIP = getClientIP(httpRequest);
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        String sessionId = httpRequest.getSession().getId();
        String user = httpRequest.getRemoteUser() != null ? httpRequest.getRemoteUser() : "anonymous";
        
        // Log de la petición entrante
        logger.infof("🔹 [REQUEST] %s %s | User: %s | IP: %s | Session: %s", 
                    method, requestURI, user, clientIP, sessionId.substring(0, 8));
        
        try {
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            int status = httpResponse.getStatus();
            
            // Determinar nivel de log basado en status code y tiempo
            if (status >= 400) {
                logger.warnf("🔸 [RESPONSE] %s %s | Status: %d | Time: %dms", 
                           method, requestURI, status, duration);
            } else if (duration > 500) {
                logger.warnf("⚠️ [SLOW] %s %s | Status: %d | Time: %dms", 
                           method, requestURI, status, duration);
            } else {
                logger.infof("✅ [RESPONSE] %s %s | Status: %d | Time: %dms", 
                           method, requestURI, status, duration);
            }
        }
    }
    
    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("🚀 AdvancedLoggerFilter inicializado");
    }
    
    @Override
    public void destroy() {
        logger.info("🛑 AdvancedLoggerFilter destruido");
    }
}
