package net.bounceme.chronos.filter;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class LoggerFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = request.getRemoteAddr();
        String url = httpRequest.getRequestURL().toString();
        String method = httpRequest.getMethod();
        String userAgent = httpRequest.getHeader("User-Agent");
        
        long startTime = System.currentTimeMillis();
        
        log.infof(">>> [INICIO] %s - IP: %s - Método: %s - URL: %s", 
                    new Date(), ip, method, url);
        
        chain.doFilter(request, response);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        log.infof("<<< [FIN] %s - URL: %s - Tiempo: %d ms", 
                    new Date(), url, duration);
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LoggerFilter inicializado");
    }
    
    @Override
    public void destroy() {
        log.info("LoggerFilter destruido");
    }
}
