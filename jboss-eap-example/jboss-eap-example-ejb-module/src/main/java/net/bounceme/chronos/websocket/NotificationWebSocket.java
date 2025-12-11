package net.bounceme.chronos.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.jbosslog.JBossLog;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/websocket/notifications")
@JBossLog
public class NotificationWebSocket {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        log.infof("✅ Nueva conexión WebSocket. ID: %s, Total: %d", 
                 session.getId(), sessions.size());
        
        // Enviar mensaje de bienvenida
        session.getAsyncRemote().sendText(
            "{\"type\": \"welcome\", \"message\": \"Conectado al servidor de notificaciones\"}"
        );
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessions.remove(session);
        log.infof("❌ Conexión cerrada. ID: %s, Razón: %s, Total: %d", 
                 session.getId(), closeReason.getReasonPhrase(), sessions.size());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.errorf("💥 Error en WebSocket. ID: %s, Error: %s", 
                  session.getId(), throwable.getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.infof("📨 Mensaje recibido de %s: %s", session.getId(), message);
        
        // Procesar el mensaje (podría ser JSON)
        // Por ahora, solo devolver un eco
        String response = String.format(
            "{\"type\": \"echo\", \"original\": \"%s\", \"processed\": \"Mensaje recibido\"}", 
            message.replace("\"", "\\\"")
        );
        
        // Enviar respuesta al cliente que envió el mensaje
        session.getAsyncRemote().sendText(response);
        
        // También podríamos enviar a todos los clientes
        // broadcast("Nuevo mensaje de " + session.getId() + ": " + message);
    }

    // Método para enviar notificaciones a todos los clientes conectados
    public static void broadcast(String message) {
    	synchronized (sessions) {
            sessions.stream()
                    .filter(Session::isOpen)
                    .forEach(session -> session.getAsyncRemote().sendText(message));
        }
    }
    
    // Método para obtener el número de conexiones activas
    public static int getActiveConnections() {
        return sessions.size();
    }
}
