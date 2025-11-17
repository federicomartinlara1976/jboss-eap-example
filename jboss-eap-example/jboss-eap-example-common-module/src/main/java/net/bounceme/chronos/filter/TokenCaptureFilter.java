package net.bounceme.chronos.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class TokenCaptureFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        // Capturar token antes de procesar la request
        captureTokensFromRequest(httpRequest);
        
        chain.doFilter(request, response);
    }
    
    private void captureTokensFromRequest(HttpServletRequest request) {
        try {
            String requestURI = request.getRequestURI();
            
            // Solo procesar requests que puedan contener tokens
            if (shouldProcessRequest(requestURI)) {
                
                // 1. Buscar token en fragment URL (common en OIDC redirect)
                captureTokenFromFragment(request);
                
                // 2. Buscar token en parámetros de query
                captureTokenFromQueryParameters(request);
                
                // 3. Buscar token en headers
                captureTokenFromHeaders(request);
                
                logCapturedTokens(request);
            }
            
        } catch (Exception e) {
            log.warn("Error capturando tokens: " + e.getMessage());
        }
    }
    
    private boolean shouldProcessRequest(String requestURI) {
        // Procesar todas las requests o específicas que sabemos que contienen tokens
        return true;
    }
    
    private void captureTokenFromFragment(HttpServletRequest request) {
        try {
            String fullURL = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            
            // Construir URL completa con query string
            String completeURL = queryString != null ? fullURL + "?" + queryString : fullURL;
            
            if (completeURL.contains("#")) {
                String[] parts = completeURL.split("#");
                if (parts.length > 1) {
                    String fragment = parts[1];
                    Map<String, String> fragmentParams = parseQueryString(fragment);
                    
                    // Capturar id_token del fragment
                    String idToken = fragmentParams.get("id_token");
                    if (idToken != null && !idToken.trim().isEmpty()) {
                        request.getSession().setAttribute("id_token", idToken);
                        log.infof("✅ ID Token capturado desde fragment: %s...", idToken.substring(0, Math.min(20, idToken.length())));
                    }
                    
                    // Capturar access_token también por si acaso
                    String accessToken = fragmentParams.get("access_token");
                    if (accessToken != null && !accessToken.trim().isEmpty()) {
                        request.getSession().setAttribute("access_token", accessToken);
                    }
                }
            }
        } catch (Exception e) {
            log.debug("No se pudo capturar token desde fragment: " + e.getMessage());
        }
    }
    
    private void captureTokenFromQueryParameters(HttpServletRequest request) {
        try {
            // Capturar desde parámetros normales
            String idToken = request.getParameter("id_token");
            if (idToken != null && !idToken.trim().isEmpty()) {
                request.getSession().setAttribute("id_token", idToken);
                log.infof("✅ ID Token capturado desde query params: %s...", idToken.substring(0, Math.min(20, idToken.length())));
            }
            
            String accessToken = request.getParameter("access_token");
            if (accessToken != null && !accessToken.trim().isEmpty()) {
                request.getSession().setAttribute("access_token", accessToken);
            }
        } catch (Exception e) {
            log.debug("No se pudo capturar token desde query parameters: " + e.getMessage());
        }
    }
    
    private void captureTokenFromHeaders(HttpServletRequest request) {
        try {
            // Capturar desde header Authorization (Bearer token)
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                // Guardar como access_token
                request.getSession().setAttribute("access_token", token);
                log.infof("✅ Token capturado desde Authorization header: %s...", token.substring(0, Math.min(20, token.length())));
            }
        } catch (Exception e) {
            log.debug("No se pudo capturar token desde headers: " + e.getMessage());
        }
    }
    
    private Map<String, String> parseQueryString(String queryString) {
        return Arrays.stream(queryString.split("&"))
                .map(param -> param.split("="))
                .filter(pair -> pair.length == 2)
                .collect(Collectors.toMap(
                    pair -> pair[0],
                    pair -> {
                        try {
                            return java.net.URLDecoder.decode(pair[1], "UTF-8");
                        } catch (Exception e) {
                            return pair[1];
                        }
                    }
                ));
    }
    
    private void logCapturedTokens(HttpServletRequest request) {
        // Solo loggear en debug para no saturar logs
        if (log.isDebugEnabled()) {
            String idToken = (String) request.getSession().getAttribute("id_token");
            String accessToken = (String) request.getSession().getAttribute("access_token");
            
            if (idToken != null || accessToken != null) {
                log.debugf("Tokens en sesión - ID Token: %s, Access Token: %s", 
                          idToken != null ? "PRESENTE" : "AUSENTE",
                          accessToken != null ? "PRESENTE" : "AUSENTE");
            }
        }
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("🚀 TokenCaptureFilter inicializado - Listo para capturar tokens OIDC");
    }
    
    @Override
    public void destroy() {
        log.info("🛑 TokenCaptureFilter destruido");
    }
}