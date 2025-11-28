package net.bounceme.chronos.jms;

import java.io.StringReader;
import java.util.concurrent.TimeUnit;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.extern.jbosslog.JBossLog;

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
            TimeUnit.SECONDS.sleep(1);
            
            log.infof("📬 [NotificacionMDB] Notificación enviada exitosamente a: %s", destinatario);
            
        } catch (InterruptedException e) {
            log.warn("💥 [NotificacionMDB] Interrupted!", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
        	log.error("💥 [NotificacionMDB] Error procesando JSON", e);
        }
    }
}
