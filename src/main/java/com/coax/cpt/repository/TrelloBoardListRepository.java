package com.coax.cpt.repository;

import com.coax.cpt.entity.BoardListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloBoardListRepository extends JpaRepository<BoardListEntity, String> {
}
