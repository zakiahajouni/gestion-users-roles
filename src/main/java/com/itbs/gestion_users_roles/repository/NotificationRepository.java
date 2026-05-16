package com.itbs.gestion_users_roles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itbs.gestion_users_roles.entity.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByLuFalseOrderByDateCreationDesc();

    List<Notification> findAllByOrderByDateCreationDesc();
}