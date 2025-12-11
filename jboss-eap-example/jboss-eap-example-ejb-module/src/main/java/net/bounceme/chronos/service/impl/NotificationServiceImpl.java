package net.bounceme.chronos.service.impl;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import lombok.extern.jbosslog.JBossLog;
import net.bounceme.chronos.service.NotificationService;
import net.bounceme.chronos.websocket.NotificationWebSocket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
@Startup
@JBossLog
public class NotificationServiceImpl implements NotificationService {

    @Override
	public void sendNotificationToAll(String title, String message) {
        String jsonNotification = String.format(
            "{\"type\": \"notification\", \"title\": \"%s\", \"message\": \"%s\", \"timestamp\": \"%s\"}",
            title.replace("\"", "\\\""),
            message.replace("\"", "\\\""),
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        
        NotificationWebSocket.broadcast(jsonNotification);
        log.infof("📤 Notificación enviada a todos los clientes: %s", title);
    }
    
    @Override
	public void sendSystemNotification(String message) {
        sendNotificationToAll("Sistema", message);
    }
}