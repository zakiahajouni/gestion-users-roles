package com.itbs.gestion_users_roles.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique_actions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class HistoriqueAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    private String action;

    private LocalDateTime dateAction;
}