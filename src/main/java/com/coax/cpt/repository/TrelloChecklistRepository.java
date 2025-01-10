package com.coax.cpt.repository;

import com.coax.cpt.entity.ChecklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloChecklistRepository extends JpaRepository<ChecklistEntity, String> {
}
