package net.bounceme.chronos.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.bounceme.chronos.service.NotificationService;

import java.util.HashMap;
import java.util.Map;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @EJB
    private NotificationService notificationService;
    
    @POST
    @Path("/send")
    public Response sendNotification(@QueryParam("title") String title, 
                                     @QueryParam("message") String message) {
        
        if (title == null || message == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Los parámetros 'title' y 'message' son requeridos");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        notificationService.sendNotificationToAll(title, message);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Notificación enviada a todos los clientes conectados");
        response.put("title", title);
        response.put("message", message);
        
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/test")
    public Response testNotification() {
        notificationService.sendSystemNotification("Esta es una notificación de prueba del servidor");
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Notificación de prueba enviada");
        
        return Response.ok(response).build();
    }
}
