package com.itbs.gestion_users_roles.repository;

import com.itbs.gestion_users_roles.entity.HistoriqueAction;
import com.itbs.gestion_users_roles.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoriqueActionRepository extends JpaRepository<HistoriqueAction, Long> {
    List<HistoriqueAction> findByUtilisateur(Utilisateur utilisateur);
    List<HistoriqueAction> findByDateActionBetween(LocalDateTime startDate, LocalDateTime endDate);
}
