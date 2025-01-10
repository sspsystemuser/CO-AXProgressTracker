package com.coax.cpt.service;

import com.coax.cpt.entity.CheckItemStatusEntity;
import com.coax.cpt.model.CardActionModel;
import com.coax.cpt.repository.TrelloCardActionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrelloCheckItemStatusService {
    Logger logger = LoggerFactory.getLogger(TrelloCheckItemStatusService.class);

    @Autowired
    private TrelloCardActionRepository trelloCardActionRepository;

    public void  insertOrUpdateCheckItemStatus(List<CardActionModel> cardActionModels){
        logger.info("TrelloService insertOrUpdateCheckItemStatus() method start");
        List<CheckItemStatusEntity> checkItemStatusEntities = new ArrayList<>();
        for(CardActionModel cardActionModel : cardActionModels){
            if(cardActionModel.getData() == null || cardActionModel.getData().getCheckItem() == null){
                continue;
            }
            Optional<CheckItemStatusEntity> cardActionEntityOpt = trelloCardActionRepository.findById(cardActionModel.getData().getCheckItem().getId());
            if(cardActionEntityOpt.isPresent()){
                CheckItemStatusEntity checkItemStatusEntity = cardActionEntityOpt.get();

                if(cardActionModel.getDate().after(checkItemStatusEntity.getStateUpdateDate())){
                    checkItemStatusEntity.setState(cardActionModel.getData().getCheckItem().getState());
                    checkItemStatusEntity.setStateUpdateDate(cardActionModel.getDate());
                    checkItemStatusEntities.add(checkItemStatusEntity);
                }
            }else{
                CheckItemStatusEntity checkItemStatusEntity = new CheckItemStatusEntity();
                if(cardActionModel.getData() !=null && cardActionModel.getData().getCheckItem() !=null) {
                    checkItemStatusEntity.setId(cardActionModel.getData().getCheckItem().getId());
                    checkItemStatusEntity.setState(cardActionModel.getData().getCheckItem().getState());
                    checkItemStatusEntity.setMemberId(cardActionModel.getIdMemberCreator());
                    checkItemStatusEntity.setStateUpdateDate(cardActionModel.getDate());
                    checkItemStatusEntity.setChecklistId(cardActionModel.getData().getChecklist().getId());
                    checkItemStatusEntities.add(checkItemStatusEntity);
                }
            }
        }
        trelloCardActionRepository.saveAll(checkItemStatusEntities);
        logger.info("Total {} records inserted for CheckItem Status.",checkItemStatusEntities.size());
    }
}
