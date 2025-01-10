package com.coax.cpt.handler;

import com.coax.cpt.DataImportScheduler;
import com.coax.cpt.entity.CardEntity;
import com.coax.cpt.model.CardActionModel;
import com.coax.cpt.repository.TrelloCardRepository;
import com.coax.cpt.service.TrelloService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class CheckItemStatusDataImportHandler {
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


    public void importCardStatusData() throws Exception{
        logger.info("CardActionDataImportHandler importCardStatusData() method start");
        List<CardEntity> cardEntities = trelloCardRepository.findAll();
        for(CardEntity cardEntity : cardEntities){
            logger.info("fetching actions for card {}",cardEntity.getId());
            List<CardActionModel> checkItemStatus = importFromTrelloApi(cardEntity.getId());
            if(CollectionUtils.isNotEmpty(checkItemStatus)){
                trelloService.insertOrUpdateCheckItemStatus(checkItemStatus);
            }

        }
        logger.info("CardActionDataImportHandler importCardStatusData() method end");
    }

    public List<CardActionModel> importFromTrelloApi(String cardId) throws Exception{
        String url = (trelloBaseURL + "/cards/"+cardId+"/actions?key=" + apiKey + "&token=" + token +"&limit=1000&filter=updateCheckItemStateOnCard");
        logger.info("Fetching CardActions: {}",url);
        try{
            ResponseEntity<CardActionModel[]> responseEntity = restTemplate.getForEntity(url, CardActionModel[].class);
            CardActionModel[] cardActions = responseEntity.getBody();
            //logger.info("Total CardActions Received [{}]",cardActions.length);
            return Arrays.asList(cardActions);
        }catch (Exception ex){
            logger.error("Error fetching Actions of Cards on Card for {}: " + cardId);
        }
        return null;
    }
}
