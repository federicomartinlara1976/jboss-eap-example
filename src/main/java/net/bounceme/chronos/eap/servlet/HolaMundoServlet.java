package net.bounceme.chronos.eap.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.bounceme.chronos.eap.service.HolaMundoService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/servlet-hola")
public class HolaMundoServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
	@Inject
    private HolaMundoService servicio;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Hola Mundo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + servicio.getSaludo() + "</h1>");
            out.println("<p>Este es un servlet ejecutándose en JBoss EAP 8</p>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}
