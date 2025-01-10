package com.coax.cpt.service;

import com.coax.cpt.entity.ChecklistEntity;
import com.coax.cpt.model.CheckListModel;
import com.coax.cpt.repository.TrelloCardRepository;
import com.coax.cpt.repository.TrelloChecklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrelloChecklistService {
    Logger logger = LoggerFactory.getLogger(TrelloChecklistService.class);

    @Autowired
    private TrelloChecklistRepository trelloChecklistRepository;

    public void insertOrUpdateChecklists(List<CheckListModel> checklistModels){
        logger.info("TrelloService insertOrUpdateChecklists() method start");
        List<ChecklistEntity> checklistEntities  = new ArrayList<>();
        for(CheckListModel checklistModel : checklistModels){
            Optional<ChecklistEntity> checklistEntityOpt = trelloChecklistRepository.findById(checklistModel.getId());
            if(checklistEntityOpt.isPresent()){
                ChecklistEntity checklistEntity = checklistEntityOpt.get();
                checklistEntity.setName(checklistModel.getName());
                checklistEntity.setLastUpdatedDate(new Date());
                checklistEntities.add(checklistEntity);
            }else{
                ChecklistEntity checkListEntity = new ChecklistEntity();
                checkListEntity.setId(checklistModel.getId());
                checkListEntity.setName(checklistModel.getName());
                checkListEntity.setCardId(checklistModel.getIdCard());
                checkListEntity.setCreatedDate(new Date());
                checklistEntities.add(checkListEntity);
            }
        }
        trelloChecklistRepository.saveAll(checklistEntities);
        logger.info("Total {} records inserted for checklistEntities.",checklistEntities.size());
    }
}
