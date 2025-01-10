package com.coax.cpt.service;

import com.coax.cpt.entity.BoardMemberEntity;
import com.coax.cpt.model.BoardMemberModel;
import com.coax.cpt.repository.TrelloBoardMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrelloBoardMemberService {
    Logger logger = LoggerFactory.getLogger(TrelloBoardMemberService.class);

    @Autowired
    TrelloBoardMemberRepository trelloBoardMemberRepository;

    public void insertOrUpdateBoardMembers(List<BoardMemberModel> boardMemberModels){
        logger.info("TrelloService insertOrUpdateBoardMembers() method start.");
        List<BoardMemberEntity> boardMemberEntities = new ArrayList<>();
        for(BoardMemberModel boardMemberModel : boardMemberModels){
            Optional<BoardMemberEntity> boardMemberEntityOpt = trelloBoardMemberRepository.findById(boardMemberModel.getId());
            if(boardMemberEntityOpt.isPresent()){
                BoardMemberEntity boardMemberEntity = boardMemberEntityOpt.get();
                boardMemberEntity.setLastUpdatedDate(new Date());
                boardMemberEntities.add(boardMemberEntity);
            }else{
                BoardMemberEntity boardMemberEntity = new BoardMemberEntity();
                boardMemberEntity.setId(boardMemberModel.getId());
                boardMemberEntity.setFullName(boardMemberModel.getFullName());
                boardMemberEntity.setUsername(boardMemberModel.getUsername());
                boardMemberEntity.setCreatedDate(new Date());
                boardMemberEntities.add(boardMemberEntity);
            }
        }
        trelloBoardMemberRepository.saveAll(boardMemberEntities);
        logger.info("Total {} records inserted for boardMembers.",boardMemberEntities.size());

    }
}
