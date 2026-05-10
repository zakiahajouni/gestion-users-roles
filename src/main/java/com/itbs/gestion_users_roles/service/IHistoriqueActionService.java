package com.itbs.gestion_users_roles.service;

import com.itbs.gestion_users_roles.entity.HistoriqueAction;
import com.itbs.gestion_users_roles.entity.Utilisateur;

import java.time.LocalDateTime;
import java.util.List;

public interface IHistoriqueActionService {
    List<HistoriqueAction> findAll();
    HistoriqueAction findById(Long id);
    HistoriqueAction create(HistoriqueAction historiqueAction);
    void delete(Long id);
    List<HistoriqueAction> findByUtilisateur(Utilisateur utilisateur);
    List<HistoriqueAction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    void logAction(Utilisateur utilisateur, String action);
}
