package com.coax.cpt.service;

import com.coax.cpt.entity.*;
import com.coax.cpt.model.*;
import com.coax.cpt.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class TrelloService {
    Logger logger = LoggerFactory.getLogger(TrelloService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${trello.api.key}")
    private String apiKey;

    @Value("${trello.api.token}")
    private String token;

    @Value("${trello.api.baseURL}")
    private String trelloBaseURL;

    @Autowired
    private TrelloBoardRepository trelloBoardRepository;

    @Autowired
    private TrelloCardRepository trelloCardRepository;

    @Autowired
    private TrelloChecklistRepository trelloChecklistRepository;

    @Autowired
    private TrelloCheckitemRepository trelloCheckitemRepository;

    @Autowired
    private TrelloCardActionRepository trelloCardActionRepository;

    @Autowired
    private TrelloBoardListRepository trelloBoardListRepository;

    @Autowired
    private TrelloBoardMemberRepository trelloBoardMemberRepository;

    @Autowired
    private TrelloCardCustomFieldRepository trelloCardCustomFieldRepository;

    @Autowired
    private TrelloCardBadgesRepository trelloCardBadgesRepository;

    @Autowired
    private TrelloCBoardRepository trelloCBoardRepository;

    @Autowired
    private TrelloCBoardListRepository trelloCBoardListRepository;

    @Autowired
    private TrelloCCardRepository trelloCCardRepository;

    @Autowired
    private  TrelloCCardMovementRepository trelloCCardMovementRepository;


    public void insertOrUpdateBoardMembers(List<BoardMemberModel> boardMemberModels){
        logger.info("TrelloService insertOrUpdateBoardMembers() method start.");
        List<BoardMemberEntity> boardMemberEntities = new ArrayList<>();
        for(BoardMemberModel boardMemberModel : boardMemberModels){
            try{
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
            }catch (Exception ex){
                logger.error("Error while processing boardMemberModel in insertOrUpdateBoardMembers() method for {}", boardMemberModel.getId(), ex);
            }

        }
        trelloBoardMemberRepository.saveAll(boardMemberEntities);
        logger.info("Total {} records inserted for boardMembers.",boardMemberEntities.size());
    }

    public void insertOrUpdateBoardLists(List<BoardListModel> boardListModels){
        logger.info("TrelloService insertOrUpdateBoardLists() method start ");
        List<BoardListEntity> boardListEntities = new ArrayList<>();
        for(BoardListModel boardListModel : boardListModels){
            try{
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
            }catch (Exception ex){
                logger.error("Error while processing boardListModel in insertOrUpdateBoardLists() method for {}", boardListModel.getName(), ex);
            }

        }
        trelloBoardListRepository.saveAll(boardListEntities);
        logger.info("Total {} records inserted for boardLists.",boardListEntities.size());
    }

    public void insertOrUpdateCards(List<CardModel> cardModels) {
        logger.info("TrelloService insertOrUpdateCards() method start ");
        List<CardEntity> cardEntities = new ArrayList<>();
        for(CardModel cardModel : cardModels) {
            try{
                Optional<CardEntity> cardEntityOpt = trelloCardRepository.findById(cardModel.getId());
                if(cardEntityOpt.isPresent()){
                    CardEntity cardEntity = cardEntityOpt.get();
                    cardEntity.setName(cardModel.getName());
                    if(cardModel.getBadges()!=null) {
                        cardEntity.setCheckItems(cardModel.getBadges().getCheckItems());
                        cardEntity.setCheckItemsChecked(cardModel.getBadges().getCheckItemsChecked());
                        cardEntity.setBoardListId(cardModel.getIdList());
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
            }catch (Exception ex){
                logger.error("Error while processing cardModel in insertOrUpdateCards() method for {}", cardModel.getName(), ex);
            }

        }
        trelloCardRepository.saveAll(cardEntities);
        logger.info("Total {} records inserted for cards.",cardEntities.size());
    }

    public void insertOrUpdateChecklists(List<CheckListModel> checklistModels){
        logger.info("TrelloService insertOrUpdateChecklists() method start");
        List<ChecklistEntity> checklistEntities  = new ArrayList<>();
        for(CheckListModel checklistModel : checklistModels){
            try{
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
            }catch (Exception ex){
                logger.error("Error while processing checklistModel in insertOrUpdateChecklists() method for {}", checklistModel.getName(), ex);
            }

        }
        trelloChecklistRepository.saveAll(checklistEntities);
        logger.info("Total {} records inserted for checklistEntities.",checklistEntities.size());
    }

    public void insertOrUpdateCheckItems(List<CheckItemModel> checkItemModels){
        logger.info("TrelloService insertOrUpdateCheckItems() method start");
        List<CheckitemEntity> checkitemEntities = new ArrayList<>();
        for(CheckItemModel checkItemModel : checkItemModels){
            try{
                Optional<CheckitemEntity> checkItemEntityOpt = trelloCheckitemRepository.findById(checkItemModel.getId());
                if(checkItemEntityOpt.isPresent()){
                    CheckitemEntity checkitemEntity = checkItemEntityOpt.get();
                    checkitemEntity.setName(checkItemModel.getName());
                    checkitemEntity.setStatus(checkItemModel.getState());
                    checkitemEntity.setLastUpdatedDate(new Date());
                    checkitemEntities.add(checkitemEntity);
                }else{
                    CheckitemEntity checkItemEntity = new CheckitemEntity();
                    checkItemEntity.setId(checkItemModel.getId());
                    checkItemEntity.setName(checkItemModel.getName());
                    checkItemEntity.setChecklistId(checkItemModel.getIdChecklist());
                    checkItemEntity.setStatus(checkItemModel.getState());
                    checkItemEntity.setCreatedDate(new Date());
                    checkitemEntities.add(checkItemEntity);
                }
            }catch (Exception ex){
                logger.error("Error while processing checkItemModel in insertOrUpdateCheckItems() method for {}", checkItemModel.getName(), ex);
            }
        }
        trelloCheckitemRepository.saveAll(checkitemEntities);
        logger.info("Total {} records inserted for CheckItems.",checkitemEntities.size());
    }

    public void  insertOrUpdateCheckItemStatus(List<CardActionModel> cardActionModels){
        logger.info("TrelloService insertOrUpdateCheckItemStatus() method start");
        List<CheckItemStatusEntity> checkItemStatusEntities = new ArrayList<>();
        for(CardActionModel cardActionModel : cardActionModels){
            try{
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
            }catch (Exception ex){
                logger.error("Error while processing cardActionModel in insertOrUpdateCheckItemStatus() method for {}", cardActionModel.getData().getCheckItem().getId(), ex);
            }
        }
        trelloCardActionRepository.saveAll(checkItemStatusEntities);
        logger.info("Total {} records inserted for CheckItem Status.",checkItemStatusEntities.size());
    }

    public void insertOrUpdateCardCustomField(List<CardCustomFieldModel> cardCustomFieldModels,String cardId){
        logger.info("TrelloService insertOrUpdateCardCustomField() method start");
        List<CardCustomFieldEntity> cardCustomFieldEntities = new ArrayList<>();
        for(CardCustomFieldModel cardCustomFieldModel : cardCustomFieldModels){
            try{
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
            }catch (Exception ex){
                logger.error("Error while processing cardCustomFieldModel in insertOrUpdateCardCustomField() method for {}", cardCustomFieldModel.getIdCustomField(), ex);
            }
        }
        trelloCardCustomFieldRepository.saveAll(cardCustomFieldEntities);
        logger.info("Total {} records inserted for Card CustomFields.",cardCustomFieldEntities.size());
    }

    public void insertOrUpdateCardBadge(List<CardModel> cardModels){
        logger.info("TrelloService insertOrUpdateCardBadge() method start");
        List<CardBadgesEntity> cardBadgesEntities = new ArrayList<>();
        for(CardModel cardModel : cardModels){
            try{
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
            }catch (Exception ex){
                logger.error("Error while processing CardModel in insertOrUpdateCardBadge() method for {}", cardModel.getId(), ex);
            }
        }
        trelloCardBadgesRepository.saveAll(cardBadgesEntities);
        logger.info("Total {} records inserted for Card badges.",cardBadgesEntities.size());
    }

    public void insertOrUpdateCBoard(List<CardAttachmentModel> cardAttachmentModels, String cardId, String cardName){
        logger.info("TrelloService insertOrUpdateCBoard() method start");
        List<CBoardEntity> cBoardEntities = new ArrayList<>();
        for(CardAttachmentModel cardAttachmentModel : cardAttachmentModels){
            try{
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
            }catch (Exception ex){
                logger.error("Error while processing CardAttachmentModel in insertOrUpdateCBoard() method for {}", cardAttachmentModel.getUrl(), ex);
            }
        }
        trelloCBoardRepository.saveAll(cBoardEntities);
        logger.info("Total {} records inserted for CBoards.",cBoardEntities.size());
    }

    public void insertOrUpdateCBoardLists(List<BoardListModel> boardListModels,String boardId){
        logger.info("TrelloService insertOrUpdateCBoardLists() method start ");
        List<CBoardListEntity> cBoardListEntities = new ArrayList<>();
        for(BoardListModel boardListModel : boardListModels){
            try{
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
            }catch (Exception ex){
                logger.error("Error while processing BoardListModel in insertOrUpdateCBoardLists() method for {}", boardListModel.getName(), ex);
            }
        }
        trelloCBoardListRepository.saveAll(cBoardListEntities);
        logger.info("Total {} records inserted for cBoardListEntities.",cBoardListEntities.size());
    }

    public void insertOrUpdateCCards(List<CardModel> cardModels) {
        logger.info("TrelloService insertOrUpdateCCards() method start ");
        List<CCardEntity> cCardEntities = new ArrayList<>();
        for(CardModel cardModel : cardModels) {
            try {
                Optional<CCardEntity> cardEntityOpt = trelloCCardRepository.findById(cardModel.getId());
                if (cardEntityOpt.isPresent()) {
                    CCardEntity cCardEntity = cardEntityOpt.get();
                    cCardEntity.setName(cardModel.getName());
                    cCardEntity.setBoardListId(cardModel.getIdList());
                    cCardEntity.setLastUpdatedDate(new Date());
                    cCardEntities.add(cCardEntity);
                } else {
                    CCardEntity cCardEntity = new CCardEntity();
                    cCardEntity.setId(cardModel.getId());
                    cCardEntity.setName(cardModel.getName());
                    cCardEntity.setBoardListId(cardModel.getIdList());
                    cCardEntity.setCreatedDate(new Date());
                    cCardEntities.add(cCardEntity);
                }
            }catch (Exception ex){
                logger.error("Error while processing CCardModel in insertOrUpdateCCards() method for {}", cardModel.getName(), ex);
            }
        }
        trelloCCardRepository.saveAll(cCardEntities);
        logger.info("Total {} records inserted for CCardEntity.",cCardEntities.size());
    }

    public void  insertOrUpdateCCardMovement(List<CCardMovementModel> cCardMovementModels){
        logger.info("TrelloService insertOrUpdateCCardMovement() method start");
        List<CCardMovementEntity> cCardMovementEntities = new ArrayList<>();
        for(CCardMovementModel cCardMovementModel : cCardMovementModels){
            try{
                Optional<CCardMovementEntity> cCardMovementEntityOpt = trelloCCardMovementRepository.findById(cCardMovementModel.getId());
                if(cCardMovementEntityOpt.isPresent()){
                    CCardMovementEntity cCardMovementEntity = cCardMovementEntityOpt.get();
                    cCardMovementEntity.setLastUpdatedDate(new Date());
                    cCardMovementEntity.setFromListId(cCardMovementEntity.getFromListId());
                    cCardMovementEntity.setFromListName(cCardMovementEntity.getFromListName());
                    cCardMovementEntity.setToListId(cCardMovementEntity.getToListId());
                    cCardMovementEntity.setToListName(cCardMovementEntity.getToListName());
                    cCardMovementEntities.add(cCardMovementEntity);

                }else{
                    CCardMovementEntity cCardMovementEntity = new CCardMovementEntity();
                    if(cCardMovementModel.getData() !=null) {
                        cCardMovementEntity.setId(cCardMovementModel.getId());
                        cCardMovementEntity.setCardId(cCardMovementModel.getData().getCard().getId());
                        cCardMovementEntity.setFromListId(cCardMovementModel.getData().getListBefore().getId());
                        cCardMovementEntity.setFromListName(cCardMovementModel.getData().getListBefore().getName());
                        cCardMovementEntity.setToListId(cCardMovementModel.getData().getListAfter().getId());
                        cCardMovementEntity.setToListName(cCardMovementModel.getData().getListAfter().getName());
                        cCardMovementEntity.setMovedAt(cCardMovementModel.getDate());
                        cCardMovementEntity.setCreatedDate(new Date());
                        cCardMovementEntities.add(cCardMovementEntity);
                    }
                }
            }catch (Exception ex){
                logger.error("Error while processing CCardMovementModel in insertOrUpdateCCardMovement() method for {}", cCardMovementModel.getData().getCard().getName(), ex);
            }
        }
        trelloCCardMovementRepository.saveAll(cCardMovementEntities);
        logger.info("Total {} records movement inserted  for CCardEntity.",cCardMovementEntities.size());
    }

}
