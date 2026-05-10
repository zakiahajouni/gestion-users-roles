package com.itbs.gestion_users_roles.service.impl;

import com.itbs.gestion_users_roles.entity.Utilisateur;
import com.itbs.gestion_users_roles.repository.UtilisateurRepository;
import com.itbs.gestion_users_roles.service.UtilisateurService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur not found: " + id));
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur not found with email: " + email));
    }

    @Override
    public Utilisateur create(Utilisateur utilisateur) {
        // Check if email already exists
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + utilisateur.getEmail());
        }
        utilisateur.setActif(true);
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur update(Long id, Utilisateur utilisateur) {
        Utilisateur existing = findById(id);
        existing.setNom(utilisateur.getNom());
        
        // Only update email if different and not already taken
        if (!existing.getEmail().equals(utilisateur.getEmail())) {
            if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists: " + utilisateur.getEmail());
            }
            existing.setEmail(utilisateur.getEmail());
        }
        
        existing.setMotDePasse(utilisateur.getMotDePasse());
        existing.setRole(utilisateur.getRole());
        existing.setActif(utilisateur.isActif());
        return utilisateurRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Utilisateur existing = findById(id);
        utilisateurRepository.delete(existing);
    }

    @Override
    public Utilisateur authenticate(String email, String password) {
        Utilisateur utilisateur = findByEmail(email);
        // In production, use proper password hashing (BCrypt, etc.)
        if (!utilisateur.getMotDePasse().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (!utilisateur.isActif()) {
            throw new IllegalArgumentException("User account is inactive");
        }
        return utilisateur;
    }

    @Override
    public List<Utilisateur> findByActif(boolean actif) {
        return utilisateurRepository.findAll().stream()
                .filter(u -> u.isActif() == actif)
                .toList();
    }
}
