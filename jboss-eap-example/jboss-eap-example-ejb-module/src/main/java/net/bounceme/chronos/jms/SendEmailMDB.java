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
	            propertyValue = "java:/jms/topic/RegisterTimeTopic"
	        ),
	        @ActivationConfigProperty(
	            propertyName = "destinationType",
	            propertyValue = "jakarta.jms.Topic"
	        ),
	        @ActivationConfigProperty(
	            propertyName = "acknowledgeMode",
	            propertyValue = "Auto-acknowledge"
	        ),
	        @ActivationConfigProperty(
	            propertyName = "subscriptionDurability",
	            propertyValue = "Durable"
	        ),
	        @ActivationConfigProperty(
	            propertyName = "subscriptionName",
	            propertyValue = "SendEmailSubscription"
	        ),
	        @ActivationConfigProperty(
	            propertyName = "clientId",
	            propertyValue = "SendEmailClient"
	        )
	    }
	)
@JBossLog
public class SendEmailMDB implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        try {
            if (msg instanceof ObjectMessage message) {
                RegisterTimeDTO messageContent = message.getBody(RegisterTimeDTO.class);
                
                log.infof("📨 [SendEmailMDB] Mensaje recibido: %s", messageContent.toString());
                
                // Procesar el mensaje JSON
                procesarRegistro(messageContent);
                
            } else {
                log.warnf("❌ [SendEmailMDB] Tipo de mensaje no soportado: %s", msg.getClass().getName());
            }
        } catch (JMSException e) {
            log.error("💥 [SendEmailMDB] Error procesando mensaje", e);
        }
    }
    
    private void procesarRegistro(RegisterTimeDTO registerTimeDTO) {
    	log.infof("📨 [SendEmailMDB] Enviando email a: %s", registerTimeDTO.getUsuario());
    }
}
