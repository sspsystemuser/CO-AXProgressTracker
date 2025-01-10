package com.coax.cpt.service;

import com.coax.cpt.entity.CardEntity;
import com.coax.cpt.model.CardModel;
import com.coax.cpt.repository.TrelloCardRepository;
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
public class TrelloCardService {
    Logger logger = LoggerFactory.getLogger(TrelloCardService.class);
    @Autowired
    private TrelloCardRepository trelloCardRepository;

    public void insertOrUpdateCards(List<CardModel> cardModels) {
        logger.info("TrelloService insertOrUpdateCards() method start ");
        List<CardEntity> cardEntities = new ArrayList<>();
        for(CardModel cardModel : cardModels) {
            Optional<CardEntity> cardEntityOpt = trelloCardRepository.findById(cardModel.getId());
            if(cardEntityOpt.isPresent()){
                CardEntity cardEntity = cardEntityOpt.get();
                cardEntity.setName(cardModel.getName());
                if(cardModel.getBadges()!=null) {
                    cardEntity.setCheckItems(cardModel.getBadges().getCheckItems());
                    cardEntity.setCheckItemsChecked(cardModel.getBadges().getCheckItemsChecked());
                }
                cardEntity.setLastUpdatedDate(new Date());
                cardEntities.add(cardEntity);
            }else{
                CardEntity cardEntity = new CardEntity();
                cardEntity.setId(cardModel.getId());
                cardEntity.setName(cardModel.getName());
                cardEntity.setBoardListId(cardModel.getIdList());
                if(cardModel.getBadges()!=null) {
                    cardEntity.setCheckItems(cardModel.getBadges().getCheckItems());
                    cardEntity.setCheckItemsChecked(cardModel.getBadges().getCheckItemsChecked());
                }
                cardEntity.setCreatedDate(new Date());
                cardEntities.add(cardEntity);
            }
        }
        trelloCardRepository.saveAll(cardEntities);
        logger.info("Total {} records inserted for cards.",cardEntities.size());
    }
}
