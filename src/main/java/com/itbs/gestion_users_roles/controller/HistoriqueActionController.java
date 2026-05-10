package com.itbs.gestion_users_roles.controller;

import com.itbs.gestion_users_roles.entity.HistoriqueAction;
import com.itbs.gestion_users_roles.entity.Utilisateur;
import com.itbs.gestion_users_roles.service.HistoriqueActionService;
import com.itbs.gestion_users_roles.service.UtilisateurService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/historique-actions")
public class HistoriqueActionController {

    private final HistoriqueActionService historiqueActionService;
    private final UtilisateurService utilisateurService;

    public HistoriqueActionController(HistoriqueActionService historiqueActionService, UtilisateurService utilisateurService) {
        this.historiqueActionService = historiqueActionService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<HistoriqueAction> getAll() {
        return historiqueActionService.findAll();
    }

    @GetMapping("/{id}")
    public HistoriqueAction getById(@PathVariable Long id) {
        return historiqueActionService.findById(id);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public List<HistoriqueAction> getByUtilisateur(@PathVariable Long utilisateurId) {
        Utilisateur utilisateur = utilisateurService.findById(utilisateurId);
        return historiqueActionService.findByUtilisateur(utilisateur);
    }

    @GetMapping("/date-range")
    public List<HistoriqueAction> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return historiqueActionService.findByDateRange(startDate, endDate);
    }

    @PostMapping
    public ResponseEntity<HistoriqueAction> create(@RequestBody HistoriqueAction historiqueAction) {
        HistoriqueAction created = historiqueActionService.create(historiqueAction);
        return ResponseEntity.created(URI.create("/api/historique-actions/" + created.getId())).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        historiqueActionService.delete(id);
    }

    @PostMapping("/log")
    public ResponseEntity<Void> logAction(@RequestParam Long utilisateurId, @RequestParam String action) {
        Utilisateur utilisateur = utilisateurService.findById(utilisateurId);
        historiqueActionService.logAction(utilisateur, action);
        return ResponseEntity.ok().build();
    }
}
