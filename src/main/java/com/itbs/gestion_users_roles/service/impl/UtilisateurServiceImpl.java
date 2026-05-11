package com.itbs.gestion_users_roles.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.itbs.gestion_users_roles.entity.Utilisateur;
import com.itbs.gestion_users_roles.repository.UtilisateurRepository;
import com.itbs.gestion_users_roles.service.UtilisateurService;

@Service
@Transactional

public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
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

    // check duplicate email
    if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Cet email est déjà utilisé"
        );
    }

    utilisateur.setActif(true);

    utilisateur.setMotDePasse(
            passwordEncoder.encode(utilisateur.getMotDePasse())
    );

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
        // Hash password if changed
        if (!utilisateur.getMotDePasse().isEmpty() && !passwordEncoder.matches(utilisateur.getMotDePasse(), existing.getMotDePasse())) {
            existing.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        }
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
        Optional<Utilisateur> optionalUser = utilisateurRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable");
        }
        Utilisateur utilisateur = optionalUser.get();
        if (!utilisateur.isActif()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User account is inactive");
        }
        if (!passwordEncoder.matches(password, utilisateur.getMotDePasse())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Mot de passe incorrect");
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
