package com.itbs.gestion_users_roles.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itbs.gestion_users_roles.entity.Notification;
import com.itbs.gestion_users_roles.repository.NotificationRepository;
import com.itbs.gestion_users_roles.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAllByOrderByDateCreationDesc();
    }

    @Override
    public List<Notification> findUnread() {
        return notificationRepository.findByLuFalseOrderByDateCreationDesc();
    }

    @Override
    public Notification markAsRead(Long id) {

        Notification notif = notificationRepository.findById(id)
                .orElseThrow();

        notif.setLu(true);

        return notificationRepository.save(notif);
    }
}