package com.coax.cpt.repository;

import com.coax.cpt.entity.CCardMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloCCardMovementRepository extends JpaRepository<CCardMovementEntity, String> {
}
