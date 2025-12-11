package net.bounceme.chronos.service;

public interface NotificationService {

	void sendNotificationToAll(String title, String message);

	void sendSystemNotification(String message);

}