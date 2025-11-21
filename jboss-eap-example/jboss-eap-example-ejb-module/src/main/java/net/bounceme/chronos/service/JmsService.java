package net.bounceme.chronos.service;

public interface JmsService {

	void enviarMensaje(String mensaje);

	/**
	 * Enviar notificación a la cola
	 */
	void enviarNotificacion(String tipo, String destinatario, String contenido);

	/**
	 * Enviar pedido a la cola
	 */
	void enviarPedido(String pedidoId, String usuario, double total);

	/**
	 * Publicar evento al topic (todos los suscriptores lo reciben)
	 */
	void publicarEvento(String tipoEvento, String usuario, String detalles);

}