package com.coax.cpt.service;

import com.coax.cpt.entity.CBoardListEntity;
import com.coax.cpt.model.BoardListModel;
import com.coax.cpt.repository.TrelloCBoardListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrelloCBoardListService {
    Logger logger = LoggerFactory.getLogger(TrelloCBoardListService.class);

    @Autowired
    private TrelloCBoardListRepository trelloCBoardListRepository;

    public void insertOrUpdateCBoardLists(List<BoardListModel> boardListModels, String boardId){
        logger.info("TrelloService insertOrUpdateCBoardLists() method start ");
        List<CBoardListEntity> cBoardListEntities = new ArrayList<>();
        for(BoardListModel boardListModel : boardListModels){
            Optional<CBoardListEntity> cBoardListEntityOpt = trelloCBoardListRepository.findById(boardListModel.getId());
            if(cBoardListEntityOpt.isPresent()){
                CBoardListEntity cBoardListEntity = cBoardListEntityOpt.get();
                cBoardListEntity.setName(boardListModel.getName());
                cBoardListEntity.setLastUpdatedDate(new Date());
                cBoardListEntities.add(cBoardListEntity);
            }else{
                CBoardListEntity cBoardListEntity = new CBoardListEntity();
                cBoardListEntity.setId(boardListModel.getId());
                cBoardListEntity.setName(boardListModel.getName());
                cBoardListEntity.setIdBoard(boardId);
                cBoardListEntity.setCreatedDate(new Date());
                cBoardListEntities.add(cBoardListEntity);
            }
        }
        trelloCBoardListRepository.saveAll(cBoardListEntities);
        logger.info("Total {} records inserted for cBoardListEntities.",cBoardListEntities.size());
    }
}
