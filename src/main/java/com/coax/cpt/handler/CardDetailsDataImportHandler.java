package com.coax.cpt.handler;

import com.coax.cpt.DataImportScheduler;
import com.coax.cpt.entity.CardEntity;
import com.coax.cpt.model.*;
import com.coax.cpt.repository.TrelloCardRepository;
import com.coax.cpt.service.TrelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CardDetailsDataImportHandler {

    Logger logger = LoggerFactory.getLogger(DataImportScheduler.class);
    @Autowired
    TrelloService trelloService;

    @Autowired
    TrelloCardRepository trelloCardRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${trello.api.key}")
    private String apiKey;

    @Value("${trello.api.token}")
    private String token;

    @Value("${trello.api.baseURL}")
    private String trelloBaseURL;

    public void importCardDetailsStatusData() throws Exception{
        logger.info("CardDetailsDataImportHandler importCardDetailsStatusData() method start");
        List<CardEntity> cardEntities = trelloCardRepository.findAll();
        logger.info("Total No of Cards {}",cardEntities.size());
        for(CardEntity cardEntity : cardEntities){
            logger.info("fetching card details for card {}",cardEntity.getId());
            CardDetailsModel cardDetailsModel = importFromTrelloApi(cardEntity.getId());
            if(cardDetailsModel != null){
                List<CheckListModel> checkListModels = cardDetailsModel.getChecklists();
                trelloService.insertOrUpdateChecklists(checkListModels);
                for(CheckListModel checkListModel : checkListModels){
                    List<CheckItemModel> checkItemModels = checkListModel.getCheckItems();
                    trelloService.insertOrUpdateCheckItems(checkItemModels);
                }
                List<CardActionModel> cardActionModels = cardDetailsModel.getActions();
                //trelloService.insertOrUpdateCheckItemStatus(cardActionModels);
                List<CardCustomFieldModel> customFieldModels = cardDetailsModel.getCustomFieldItems();
                trelloService.insertOrUpdateCardCustomField(customFieldModels,cardEntity.getId());
                List<CardAttachmentModel> cardAttachmentModels = cardDetailsModel.getAttachments();
                trelloService.insertOrUpdateCBoard(cardAttachmentModels,cardEntity.getId(),cardEntity.getName());
            }

        }
        logger.info("CardDetailsDataImportHandler importCardDetailsStatusData() method end");
    }

    //{{protocol}}://{{host}}/{{basePath}}cards/:id?actions=all&checklists=all&checklist_fields=all&customFieldItems=true
    public CardDetailsModel importFromTrelloApi(String cardId) throws Exception{
        String url = (trelloBaseURL + "/cards/"+cardId+"?key="+ apiKey + "&token=" + token+"&actions=all&attachments=true&attachment_fields=all&checklists=all&checklist_fields=all&customFieldItems=true");
        logger.info("Fetching CardDetail Status: {}",url);
        try{
            ResponseEntity<CardDetailsModel> responseEntity = restTemplate.getForEntity(url, CardDetailsModel.class);
            CardDetailsModel cardDetailsModel = responseEntity.getBody();
            return cardDetailsModel;
        }catch (Exception ex){
            logger.error("Error fetching details of Cards on Card for {}: " + cardId);
        }
        return null;
    }

}
