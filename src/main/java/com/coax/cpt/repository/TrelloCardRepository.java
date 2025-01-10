package com.coax.cpt.repository;

import com.coax.cpt.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrelloCardRepository extends JpaRepository<CardEntity, String> {

}
