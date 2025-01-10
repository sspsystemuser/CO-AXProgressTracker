package com.coax.cpt.repository;

import com.coax.cpt.entity.CardBadgesEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloCardBadgesRepository extends JpaRepository<CardBadgesEntity,String> {
}
