package com.itbs.gestion_users_roles.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.itbs.gestion_users_roles.entity.Utilisateur;
import com.itbs.gestion_users_roles.service.UtilisateurService;

import lombok.Data;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<Utilisateur> getAll() {
        return utilisateurService.findAll();
    }

    @GetMapping("/{id}")
    public Utilisateur getById(@PathVariable Long id) {
        return utilisateurService.findById(id);
    }

    @GetMapping("/email/{email}")
    public Utilisateur getByEmail(@PathVariable String email) {
        return utilisateurService.findByEmail(email);
    }

    @GetMapping("/actif/{actif}")
    public List<Utilisateur> getByActif(@PathVariable boolean actif) {
        return utilisateurService.findByActif(actif);
    }

    @PostMapping
    public ResponseEntity<Utilisateur> create(@RequestBody Utilisateur utilisateur) {
        Utilisateur created = utilisateurService.create(utilisateur);
        return ResponseEntity.created(URI.create("/api/utilisateurs/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Utilisateur update(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        return utilisateurService.update(id, utilisateur);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        utilisateurService.delete(id);
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/authenticate")
    public Utilisateur authenticate(@RequestBody LoginRequest loginRequest) {
        return utilisateurService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}
