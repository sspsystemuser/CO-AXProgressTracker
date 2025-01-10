package com.coax.cpt.repository;

import com.coax.cpt.entity.CBoardListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloCBoardListRepository extends JpaRepository<CBoardListEntity,String> {
}
