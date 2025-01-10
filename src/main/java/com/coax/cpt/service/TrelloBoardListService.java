package com.coax.cpt.service;

import com.coax.cpt.entity.BoardListEntity;
import com.coax.cpt.model.BoardListModel;
import com.coax.cpt.repository.TrelloBoardListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrelloBoardListService {
    Logger logger = LoggerFactory.getLogger(TrelloBoardListService.class);
    @Autowired
    private TrelloBoardListRepository trelloBoardListRepository;

    public void insertOrUpdateBoardLists(List<BoardListModel> boardListModels){
        logger.info("TrelloService insertOrUpdateBoardLists() method start ");
        List<BoardListEntity> boardListEntities = new ArrayList<>();
        for(BoardListModel boardListModel : boardListModels){
            Optional<BoardListEntity> boardListEntityOpt = trelloBoardListRepository.findById(boardListModel.getId());
            if(boardListEntityOpt.isPresent()){
                BoardListEntity boardListEntity = boardListEntityOpt.get();
                boardListEntity.setName(boardListModel.getName());
                boardListEntity.setIdBoard(boardListModel.getIdBoard());
                boardListEntity.setLastUpdatedDate(new Date());
                boardListEntities.add(boardListEntity);
            }else{
                BoardListEntity boardListEntity = new BoardListEntity();
                boardListEntity.setId(boardListModel.getId());
                boardListEntity.setName(boardListModel.getName());
                boardListEntity.setIdBoard(boardListModel.getIdBoard());
                boardListEntity.setCreatedDate(new Date());
                boardListEntities.add(boardListEntity);
            }
        }
        trelloBoardListRepository.saveAll(boardListEntities);
        logger.info("Total {} records inserted for boardLists.",boardListEntities.size());
    }
}
