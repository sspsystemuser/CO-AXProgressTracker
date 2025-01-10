package com.coax.cpt.repository;

import com.coax.cpt.entity.CheckitemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloCheckitemRepository extends JpaRepository<CheckitemEntity,String> {
}
