package com.itbs.gestion_users_roles.service.impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.itbs.gestion_users_roles.entity.Role;
import com.itbs.gestion_users_roles.entity.Utilisateur;
import com.itbs.gestion_users_roles.repository.RoleRepository;
import com.itbs.gestion_users_roles.repository.UtilisateurRepository;
import com.itbs.gestion_users_roles.service.EmailService;
import com.itbs.gestion_users_roles.service.UtilisateurService;

@Service
@Transactional

public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RoleRepository roleRepository;

  public UtilisateurServiceImpl(
        UtilisateurRepository utilisateurRepository,
        EmailService emailService,
        RoleRepository roleRepository
) {

    this.utilisateurRepository = utilisateurRepository;
    this.emailService = emailService;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.roleRepository = roleRepository;
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

    if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Cet email est déjà utilisé"
        );
    }

    // 🔥 FORCE ROLE USER (id = 3)
    Role userRole = roleRepository.findById(3L)
            .orElseThrow(() -> new RuntimeException("Role USER introuvable"));

    utilisateur.setRole(userRole);

    utilisateur.setActif(false);
    utilisateur.setMotDePasse(null);

    return utilisateurRepository.save(utilisateur);
}

@Override
public Utilisateur update(Long id, Utilisateur utilisateur) {

    Utilisateur existing = findById(id);

    boolean wasInactive = !existing.isActif();
    boolean willBeActive = utilisateur.isActif();

    existing.setNom(utilisateur.getNom());

    // EMAIL
    if (!existing.getEmail().equals(utilisateur.getEmail())) {

        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + utilisateur.getEmail());
        }

        existing.setEmail(utilisateur.getEmail());
    }

    // ROLE
    existing.setRole(utilisateur.getRole());

    // =========================
    // ACTIVATION LOGIC
    // =========================
    if (wasInactive && willBeActive) {

        String rawPassword = generateRandomPassword();

        existing.setMotDePasse(passwordEncoder.encode(rawPassword));
        existing.setActif(true);

        utilisateurRepository.save(existing);

        // ENVOI EMAIL
        emailService.sendAccountActivatedEmail(
                existing.getEmail(),
                existing.getNom(),
                rawPassword
        );

        return existing;
    }

    // SIMPLE UPDATE (no activation)
    existing.setActif(willBeActive);

    return utilisateurRepository.save(existing);
}
    @Override
    public void delete(Long id) {
        Utilisateur existing = findById(id);
        utilisateurRepository.delete(existing);
    }

@Override
public Utilisateur authenticate(String email, String password) {

    Optional<Utilisateur> optionalUser =
            utilisateurRepository.findByEmail(email);

    if (optionalUser.isEmpty()) {

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Utilisateur introuvable"
        );
    }

    Utilisateur utilisateur = optionalUser.get();

    if (!utilisateur.isActif()) {

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Votre compte n'est pas encore actif. Vous êtes en attente de l'approbation de l'administrateur."
        );
    }

    if (!passwordEncoder.matches(
            password,
            utilisateur.getMotDePasse()
    )) {

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Mot de passe incorrect"
        );
    }

    return utilisateur;
}

    @Override
    public List<Utilisateur> findByActif(boolean actif) {
        return utilisateurRepository.findAll().stream()
                .filter(u -> u.isActif() == actif)
                .toList();
    }
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }
    
}
