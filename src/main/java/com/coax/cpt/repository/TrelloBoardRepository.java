package com.coax.cpt.repository;

import com.coax.cpt.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloBoardRepository extends JpaRepository<BoardEntity,String> {
}
