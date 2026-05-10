package com.itbs.gestion_users_roles.controller;

import com.itbs.gestion_users_roles.entity.Utilisateur;
import com.itbs.gestion_users_roles.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @PostMapping("/authenticate")
    public Utilisateur authenticate(@RequestParam String email, @RequestParam String password) {
        return utilisateurService.authenticate(email, password);
    }
}
