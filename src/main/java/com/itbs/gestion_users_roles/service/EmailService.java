package com.itbs.gestion_users_roles.service;

public interface EmailService {

    void sendAccountActivatedEmail(
            String to,
            String nom,
            String password
    );
}