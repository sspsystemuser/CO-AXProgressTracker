package com.coax.cpt.service;

import com.coax.cpt.entity.CBoardEntity;
import com.coax.cpt.model.CardAttachmentModel;
import com.coax.cpt.repository.TrelloCBoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TrelloCBoardService {
    Logger logger = LoggerFactory.getLogger(TrelloCBoardService.class);

    @Autowired
    private TrelloCBoardRepository trelloCBoardRepository;

    public void insertOrUpdateCBoard(List<CardAttachmentModel> cardAttachmentModels, String cardId, String cardName){
        logger.info("TrelloService insertOrUpdateCBoard() method start");
        List<CBoardEntity> cBoardEntities = new ArrayList<>();
        for(CardAttachmentModel cardAttachmentModel : cardAttachmentModels){
            //Optional<CompanyBoardEntity> boardEntityOpt = trelloCompanyBoardRepository.findById(cardId);
            if(cardAttachmentModel.getUrl()== null){
                continue;
            }
            String boardIdPattern = "https://trello.com/b/([a-zA-Z0-9]+)/?.*";
            Pattern pattern = Pattern.compile(boardIdPattern);
            Matcher matcher = pattern.matcher(cardAttachmentModel.getUrl());
            if(matcher.find()){
                CBoardEntity cBoardEntity = new CBoardEntity();
                cBoardEntity.setId(matcher.group(1));
                cBoardEntity.setName(cardName);
                cBoardEntity.setParentCardId(cardId);
                cBoardEntity.setCreatedDate(new Date());
                cBoardEntities.add(cBoardEntity);
            }
        }
        trelloCBoardRepository.saveAll(cBoardEntities);
        logger.info("Total {} records inserted for CBoards.",cBoardEntities.size());
    }
    private String extractBoardId(String desc) {
        int start = desc.indexOf("https://trello.com/b/") + 21; // URL starts after this index
        int end = desc.indexOf("/", start);
        return desc.substring(start, end); // Extract the board ID from URL
    }
}
