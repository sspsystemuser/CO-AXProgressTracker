package com.coax.cpt.repository;

import com.coax.cpt.entity.BoardMemberEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrelloBoardMemberRepository extends JpaRepository<BoardMemberEntity,String> {
}
