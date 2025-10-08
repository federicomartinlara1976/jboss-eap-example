package net.bounceme.chronos.eap.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@WebFilter("/*")
public class LoggerFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = request.getRemoteAddr();
        String url = httpRequest.getRequestURL().toString();
        String method = httpRequest.getMethod();
        
        long startTime = System.currentTimeMillis();
        
        System.out.println(">>> [LoggerFilter] " + new Date() + 
                          " - IP: " + ip + 
                          " - Método: " + method + 
                          " - URL: " + url);
        
        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("<<< [LoggerFilter] Tiempo de ejecución: " + duration + "ms");
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoggerFilter inicializado");
    }
    
    @Override
    public void destroy() {
        System.out.println("LoggerFilter destruido");
    }
}
