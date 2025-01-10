package com.coax.cpt.repository;


import com.coax.cpt.entity.CardBadgesEntity;
import com.coax.cpt.entity.CardCustomFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloCardCustomFieldRepository extends JpaRepository<CardCustomFieldEntity,String> {
}
