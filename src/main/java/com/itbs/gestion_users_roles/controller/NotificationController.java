package com.itbs.gestion_users_roles.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.itbs.gestion_users_roles.entity.Notification;
import com.itbs.gestion_users_roles.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // GET ALL
    @GetMapping
    public List<Notification> getAll() {
        return notificationService.findAll();
    }

    // GET UNREAD
    @GetMapping("/unread")
    public List<Notification> getUnread() {
        return notificationService.findUnread();
    }

    // MARK AS READ
    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable Long id) {
        return notificationService.markAsRead(id);
    }
}