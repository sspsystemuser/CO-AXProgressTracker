package com.coax.cpt.repository;

import com.coax.cpt.entity.CCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloCCardRepository extends JpaRepository<CCardEntity,String> {
}
