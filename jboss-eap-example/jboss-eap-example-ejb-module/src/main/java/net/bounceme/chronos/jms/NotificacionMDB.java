package net.bounceme.chronos.jms;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import lombok.extern.jbosslog.JBossLog;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "destinationLookup",
            propertyValue = "java:/jms/queue/NotificacionQueue"
        ),
        @ActivationConfigProperty(
            propertyName = "destinationType",
            propertyValue = "jakarta.jms.Queue"
        ),
        @ActivationConfigProperty(
            propertyName = "acknowledgeMode",
            propertyValue = "Auto-acknowledge"
        )
    }
)
@JBossLog
public class NotificacionMDB implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        try {
            if (msg instanceof TextMessage message) {
                String messageContent = message.getText();
                
                log.infof("📨 [NotificacionMDB] Mensaje recibido: %s", messageContent);
                
                // Procesar el mensaje JSON
                procesarNotificacion(messageContent);
                
            } else {
                log.warn("❌ [NotificacionMDB] Tipo de mensaje no soportado: " + msg.getClass().getName());
            }
        } catch (JMSException e) {
            log.error("💥 [NotificacionMDB] Error procesando mensaje", e);
        }
    }
    
    private void procesarNotificacion(String jsonContent) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonContent))) {
            JsonObject jsonObject = jsonReader.readObject();
            
            String tipo = jsonObject.getString("tipo", "DESCONOCIDO");
            String destinatario = jsonObject.getString("destinatario", "DESCONOCIDO");
            String contenido = jsonObject.getString("contenido", "");
            
            log.infof("✅ [NotificacionMDB] Procesando notificación - Tipo: %s, Para: %s, Contenido: %s", 
                     tipo, destinatario, contenido);
            
            // Simular procesamiento
            Thread.sleep(1000);
            
            log.infof("📬 [NotificacionMDB] Notificación enviada exitosamente a: %s", destinatario);
            
        } catch (Exception e) {
            log.error("💥 [NotificacionMDB] Error procesando JSON", e);
        }
    }
}
