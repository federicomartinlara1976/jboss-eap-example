package net.bounceme.chronos.jms;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import lombok.extern.jbosslog.JBossLog;
import net.bounceme.chronos.dto.RegisterTimeDTO;

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(
            propertyName = "destinationLookup",
            propertyValue = "java:/jms/queue/RegisterTimeQueue"
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
public class RegisterTimeMDB implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        try {
            if (msg instanceof ObjectMessage message) {
                RegisterTimeDTO messageContent = message.getBody(RegisterTimeDTO.class);
                
                log.infof("📨 [RegisterTimeMDB] Mensaje recibido: %s", messageContent.toString());
                
                // Procesar el mensaje JSON
                procesarRegistro(messageContent);
                
            } else {
                log.warn("❌ [NotificacionMDB] Tipo de mensaje no soportado: " + msg.getClass().getName());
            }
        } catch (JMSException e) {
            log.error("💥 [NotificacionMDB] Error procesando mensaje", e);
        }
    }
    
    private void procesarRegistro(RegisterTimeDTO registerTimeDTO) {
    	log.infof("📨 [RegisterTimeMDB] Registrando: %s", registerTimeDTO.toString());
    }
}
