package com.coax.cpt.service;

import com.coax.cpt.entity.CardCustomFieldEntity;
import com.coax.cpt.model.CardCustomFieldModel;
import com.coax.cpt.repository.TrelloCardCustomFieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrelloCardCustomFieldService {
    Logger logger = LoggerFactory.getLogger(TrelloCardCustomFieldService.class);

    @Autowired
    private TrelloCardCustomFieldRepository trelloCardCustomFieldRepository;

    public void insertOrUpdateCardCustomField(List<CardCustomFieldModel> cardCustomFieldModels, String cardId){
        logger.info("TrelloService insertOrUpdateCardCustomField() method start");
        List<CardCustomFieldEntity> cardCustomFieldEntities = new ArrayList<>();
        for(CardCustomFieldModel cardCustomFieldModel : cardCustomFieldModels){
            if(cardCustomFieldModel.getIdCustomField().equals("61375461a0e463167ce08c41")){
                if(cardCustomFieldModel.getValue()==null)
                    continue;

                Optional<CardCustomFieldEntity> cardCustomFieldEntityOpt =  trelloCardCustomFieldRepository.findById(cardId);
                if(cardCustomFieldEntityOpt.isPresent()){
                    CardCustomFieldEntity cardCustomFieldEntity = cardCustomFieldEntityOpt.get();
                    cardCustomFieldEntity.setLastUpdatedDate(new Date());
                    cardCustomFieldEntities.add(cardCustomFieldEntity);
                }else{
                    CardCustomFieldEntity cardCustomFieldEntity = new CardCustomFieldEntity();
                    cardCustomFieldEntity.setCustomFieldId(cardCustomFieldModel.getIdCustomField());
                    cardCustomFieldEntity.setCardId(cardId);
                    cardCustomFieldEntity.setStartDate(cardCustomFieldModel.getValue().getDate());
                    cardCustomFieldEntity.setCreatedDate(new Date());
                    cardCustomFieldEntities.add(cardCustomFieldEntity);
                }
            }

        }
        trelloCardCustomFieldRepository.saveAll(cardCustomFieldEntities);
        logger.info("Total {} records inserted for Card CustomFields.",cardCustomFieldEntities.size());
    }
}
