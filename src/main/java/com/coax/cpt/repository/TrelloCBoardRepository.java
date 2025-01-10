package com.coax.cpt.repository;

import com.coax.cpt.entity.CBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloCBoardRepository extends JpaRepository<CBoardEntity,String> {
}
