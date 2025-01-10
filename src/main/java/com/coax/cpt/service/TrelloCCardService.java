package com.coax.cpt.service;

import com.coax.cpt.entity.CCardEntity;
import com.coax.cpt.model.CardModel;
import com.coax.cpt.repository.TrelloCCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrelloCCardService {
    Logger logger = LoggerFactory.getLogger(TrelloCCardService.class);

    @Autowired
    private TrelloCCardRepository trelloCCardRepository;

    public void insertOrUpdateCCards(List<CardModel> cardModels) {
        logger.info("TrelloService insertOrUpdateCCards() method start ");
        List<CCardEntity> cCardEntities = new ArrayList<>();
        for(CardModel cardModel : cardModels) {
            Optional<CCardEntity> cardEntityOpt = trelloCCardRepository.findById(cardModel.getId());
            if(cardEntityOpt.isPresent()){
                CCardEntity cCardEntity = cardEntityOpt.get();
                cCardEntity.setName(cardModel.getName());
                cCardEntity.setLastUpdatedDate(new Date());
                cCardEntities.add(cCardEntity);
            }else{
                CCardEntity cCardEntity = new CCardEntity();
                cCardEntity.setId(cardModel.getId());
                cCardEntity.setName(cardModel.getName());
                cCardEntity.setBoardListId(cardModel.getIdList());
                cCardEntity.setCreatedDate(new Date());
                cCardEntities.add(cCardEntity);
            }
        }
        trelloCCardRepository.saveAll(cCardEntities);
        logger.info("Total {} records inserted for CCardEntity.",cCardEntities.size());
    }
}
