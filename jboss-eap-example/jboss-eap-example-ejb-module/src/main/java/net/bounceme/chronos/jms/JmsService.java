package net.bounceme.chronos.jms;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import jakarta.json.Json;
import lombok.extern.jbosslog.JBossLog;

@Stateless
@JBossLog
public class JmsService {

    @Resource(lookup = "java:/jms/queue/NotificacionQueue")
    private Queue notificacionQueue;

    @Resource(lookup = "java:/jms/queue/PedidoQueue")
    private Queue pedidoQueue;

    @Resource(lookup = "java:/jms/topic/EventosTopic")
    private Topic eventosTopic;

    @Resource(lookup = "java:/JmsXA")
    private ConnectionFactory connectionFactory;

    /**
     * Enviar notificación a la cola
     */
    public void enviarNotificacion(String tipo, String destinatario, String contenido) {
        String mensajeJson = crearMensajeNotificacion(tipo, destinatario, contenido);
        enviarMensajeCola(notificacionQueue, mensajeJson, "NOTIFICACION");
    }

    /**
     * Enviar pedido a la cola
     */
    public void enviarPedido(String pedidoId, String usuario, double total) {
        String mensajeJson = crearMensajePedido(pedidoId, usuario, total);
        enviarMensajeCola(pedidoQueue, mensajeJson, "PEDIDO");
    }

    /**
     * Publicar evento al topic (todos los suscriptores lo reciben)
     */
    public void publicarEvento(String tipoEvento, String usuario, String detalles) {
        String mensajeJson = crearMensajeEvento(tipoEvento, usuario, detalles);
        enviarMensajeTopic(eventosTopic, mensajeJson, "EVENTO");
    }

    private void enviarMensajeCola(Queue queue, String mensaje, String tipo) {
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
             MessageProducer producer = session.createProducer(queue)) {
            
            TextMessage textMessage = session.createTextMessage(mensaje);
            textMessage.setStringProperty("Tipo", tipo);
            textMessage.setLongProperty("Timestamp", System.currentTimeMillis());
            
            producer.send(textMessage);
            
            log.infof("📤 [JmsService] Mensaje %s enviado a cola: %s", tipo, mensaje);
            
        } catch (JMSException e) {
            log.error("💥 [JmsService] Error enviando mensaje a cola", e);
            throw new RuntimeException("Error enviando mensaje JMS", e);
        }
    }

    private void enviarMensajeTopic(Topic topic, String mensaje, String tipo) {
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
             MessageProducer producer = session.createProducer(topic)) {
            
            TextMessage textMessage = session.createTextMessage(mensaje);
            textMessage.setStringProperty("Tipo", tipo);
            textMessage.setLongProperty("Timestamp", System.currentTimeMillis());
            
            producer.send(textMessage);
            
            log.infof("📢 [JmsService] Evento %s publicado a topic: %s", tipo, mensaje);
            
        } catch (JMSException e) {
            log.error("💥 [JmsService] Error publicando evento a topic", e);
            throw new RuntimeException("Error publicando evento JMS", e);
        }
    }

    private String crearMensajeNotificacion(String tipo, String destinatario, String contenido) {
        return Json.createObjectBuilder()
                .add("tipo", tipo)
                .add("destinatario", destinatario)
                .add("contenido", contenido)
                .add("timestamp", System.currentTimeMillis())
                .build()
                .toString();
    }

    private String crearMensajePedido(String pedidoId, String usuario, double total) {
        return Json.createObjectBuilder()
                .add("pedidoId", pedidoId)
                .add("usuario", usuario)
                .add("total", total)
                .add("timestamp", System.currentTimeMillis())
                .build()
                .toString();
    }

    private String crearMensajeEvento(String tipoEvento, String usuario, String detalles) {
        return Json.createObjectBuilder()
                .add("tipoEvento", tipoEvento)
                .add("usuario", usuario)
                .add("detalles", detalles)
                .add("timestamp", System.currentTimeMillis())
                .build()
                .toString();
    }
}