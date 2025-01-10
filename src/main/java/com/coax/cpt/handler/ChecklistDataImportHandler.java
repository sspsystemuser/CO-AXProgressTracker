package com.coax.cpt.handler;

import com.coax.cpt.DataImportScheduler;
import com.coax.cpt.entity.CardEntity;
import com.coax.cpt.model.CheckListModel;
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
public class ChecklistDataImportHandler {
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


    public void importChecklistData() throws Exception{
        logger.info("ChecklistDataImportHandler importChecklistData() method start");
        List<CardEntity> cardEntities = trelloCardRepository.findAll();
        for(CardEntity cardEntity : cardEntities){
            logger.info("fetching checklist for card {}",cardEntity.getId());
            List<CheckListModel> checklists = importFromTrelloApi(cardEntity.getId());
            if(CollectionUtils.isNotEmpty(checklists)){
                trelloService.insertOrUpdateChecklists(checklists);
            }

        }
        logger.info("ChecklistDataImportHandler importChecklistData() method start");
    }

    public List<CheckListModel> importFromTrelloApi(String cardId) throws Exception{
        String url = (trelloBaseURL + "/cards/"+cardId+"/checklists?key=" + apiKey + "&token=" + token);
        logger.info("Fetching Checklist for card: {}",url);
        try{
            ResponseEntity<CheckListModel[]> responseEntity = restTemplate.getForEntity(url, CheckListModel[].class);
            CheckListModel[] checklists = responseEntity.getBody();
            //logger.info("Fetching CardActions Success. Received [{}]",checklists.length);
            return Arrays.asList(checklists);
        }catch (Exception ex){
            logger.error("Error fetching Checklists  of Cards on Card for {}: " + cardId);
        }
        return null;
    }

}
