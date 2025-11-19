package net.bounceme.chronos.jms;

import java.io.StringReader;

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
            propertyValue = "java:/jms/queue/PedidoQueue"
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
public class PedidoMDB implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String messageContent = textMessage.getText();
                
                log.infof("🛒 [PedidoMDB] Pedido recibido: %s", messageContent);
                
                // Procesar el pedido
                procesarPedido(messageContent);
                
            } else {
                log.warn("❌ [PedidoMDB] Tipo de mensaje no soportado: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            log.error("💥 [PedidoMDB] Error procesando pedido", e);
        }
    }
    
    private void procesarPedido(String jsonContent) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonContent))) {
            JsonObject jsonObject = jsonReader.readObject();
            
            String pedidoId = jsonObject.getString("pedidoId", "DESCONOCIDO");
            String usuario = jsonObject.getString("usuario", "DESCONOCIDO");
            double total = jsonObject.getJsonNumber("total").doubleValue();
            
            log.infof("✅ [PedidoMDB] Procesando pedido - ID: %s, Usuario: %s, Total: %.2f", 
                     pedidoId, usuario, total);
            
            // Simular procesamiento del pedido
            Thread.sleep(2000); // Simular trabajo
            
            // Registrar finalización
            log.infof("🎉 [PedidoMDB] Pedido %s procesado exitosamente para usuario %s", 
                     pedidoId, usuario);
            
        } catch (Exception e) {
            log.error("💥 [PedidoMDB] Error procesando pedido JSON", e);
        }
    }
}