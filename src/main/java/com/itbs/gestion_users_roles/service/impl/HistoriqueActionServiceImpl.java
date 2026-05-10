package com.itbs.gestion_users_roles.service.impl;

import com.itbs.gestion_users_roles.entity.HistoriqueAction;
import com.itbs.gestion_users_roles.entity.Utilisateur;
import com.itbs.gestion_users_roles.repository.HistoriqueActionRepository;
import com.itbs.gestion_users_roles.service.HistoriqueActionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class HistoriqueActionServiceImpl implements HistoriqueActionService {

    private final HistoriqueActionRepository historiqueActionRepository;

    public HistoriqueActionServiceImpl(HistoriqueActionRepository historiqueActionRepository) {
        this.historiqueActionRepository = historiqueActionRepository;
    }

    @Override
    public List<HistoriqueAction> findAll() {
        return historiqueActionRepository.findAll();
    }

    @Override
    public HistoriqueAction findById(Long id) {
        return historiqueActionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HistoriqueAction not found: " + id));
    }

    @Override
    public HistoriqueAction create(HistoriqueAction historiqueAction) {
        if (historiqueAction.getDateAction() == null) {
            historiqueAction.setDateAction(LocalDateTime.now());
        }
        return historiqueActionRepository.save(historiqueAction);
    }

    @Override
    public void delete(Long id) {
        HistoriqueAction existing = findById(id);
        historiqueActionRepository.delete(existing);
    }

    @Override
    public List<HistoriqueAction> findByUtilisateur(Utilisateur utilisateur) {
        return historiqueActionRepository.findByUtilisateur(utilisateur);
    }

    @Override
    public List<HistoriqueAction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return historiqueActionRepository.findByDateActionBetween(startDate, endDate);
    }

    @Override
    public void logAction(Utilisateur utilisateur, String action) {
        HistoriqueAction historiqueAction = HistoriqueAction.builder()
                .utilisateur(utilisateur)
                .action(action)
                .dateAction(LocalDateTime.now())
                .build();
        historiqueActionRepository.save(historiqueAction);
    }
}
