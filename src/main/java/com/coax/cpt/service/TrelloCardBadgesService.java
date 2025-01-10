package com.coax.cpt.service;

import com.coax.cpt.entity.CardBadgesEntity;
import com.coax.cpt.model.CardModel;
import com.coax.cpt.repository.TrelloCardBadgesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrelloCardBadgesService {
    Logger logger = LoggerFactory.getLogger(TrelloCardBadgesService.class);

    @Autowired
    private TrelloCardBadgesRepository trelloCardBadgesRepository;

    public void insertOrUpdateCardBadge(List<CardModel> cardModels){
        logger.info("TrelloService insertOrUpdateCardBadge() method start");
        List<CardBadgesEntity> cardBadgesEntities = new ArrayList<>();
        for(CardModel cardModel : cardModels){
            Optional<CardBadgesEntity> cardBadgesEntityOpt = trelloCardBadgesRepository.findById(cardModel.getId());
            if(cardBadgesEntityOpt.isPresent()){
                CardBadgesEntity cardBadgesEntity = cardBadgesEntityOpt.get();
                cardBadgesEntity.setLastUpdatedDate(new Date());
                cardBadgesEntity.setCheckItems(cardModel.getBadges().getCheckItems());
                cardBadgesEntity.setCheckItemsChecked(cardModel.getBadges().getCheckItemsChecked());
                cardBadgesEntities.add(cardBadgesEntity);
            }else{
                CardBadgesEntity cardBadgesEntity = new CardBadgesEntity();
                cardBadgesEntity.setCard_id(cardModel.getId());
                cardBadgesEntity.setCheckItems(cardModel.getBadges().getCheckItems());
                cardBadgesEntity.setCheckItemsChecked(cardModel.getBadges().getCheckItemsChecked());
                cardBadgesEntity.setCreatedDate(new Date());
                cardBadgesEntities.add(cardBadgesEntity);
            }
        }
        trelloCardBadgesRepository.saveAll(cardBadgesEntities);
        logger.info("Total {} records inserted for Card badges.",cardBadgesEntities.size());
    }
}
