package com.coax.cpt.repository;

import com.coax.cpt.entity.CheckItemStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloCardActionRepository extends JpaRepository<CheckItemStatusEntity, String> {


}
