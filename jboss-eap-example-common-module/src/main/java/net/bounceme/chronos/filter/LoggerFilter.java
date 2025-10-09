package net.bounceme.chronos.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

import org.jboss.logging.Logger;

public class LoggerFilter implements Filter {
    
    private static final Logger logger = Logger.getLogger(LoggerFilter.class);
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = request.getRemoteAddr();
        String url = httpRequest.getRequestURL().toString();
        String method = httpRequest.getMethod();
        String userAgent = httpRequest.getHeader("User-Agent");
        
        long startTime = System.currentTimeMillis();
        
        logger.infof(">>> [INICIO] %s - IP: %s - Método: %s - URL: %s", 
                    new Date(), ip, method, url);
        
        chain.doFilter(request, response);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        logger.infof("<<< [FIN] %s - URL: %s - Tiempo: %d ms", 
                    new Date(), url, duration);
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("LoggerFilter inicializado");
    }
    
    @Override
    public void destroy() {
        logger.info("LoggerFilter destruido");
    }
}
