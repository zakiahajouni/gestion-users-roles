package com.itbs.gestion_users_roles.service;

import com.itbs.gestion_users_roles.entity.Utilisateur;

import java.util.List;

public interface IUtilisateurService {
    List<Utilisateur> findAll();
    Utilisateur findById(Long id);
    Utilisateur findByEmail(String email);
    Utilisateur create(Utilisateur utilisateur);
    Utilisateur update(Long id, Utilisateur utilisateur);
    void delete(Long id);
    Utilisateur authenticate(String email, String password);
    List<Utilisateur> findByActif(boolean actif);
}
