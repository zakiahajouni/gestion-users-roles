package com.itbs.gestion_users_roles.service;

import java.util.List;

import com.itbs.gestion_users_roles.entity.Notification;

public interface NotificationService {

    Notification create(Notification notification);

    List<Notification> findAll();

    List<Notification> findUnread();

    Notification markAsRead(Long id);
}