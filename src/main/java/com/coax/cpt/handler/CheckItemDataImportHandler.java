package com.coax.cpt.handler;

import com.coax.cpt.DataImportScheduler;
import com.coax.cpt.entity.ChecklistEntity;
import com.coax.cpt.model.CheckItemModel;
import com.coax.cpt.repository.TrelloChecklistRepository;
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
public class CheckItemDataImportHandler {
    Logger logger = LoggerFactory.getLogger(DataImportScheduler.class);
    @Autowired
    TrelloService trelloService;

    @Autowired
    TrelloChecklistRepository trelloChecklistRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${trello.api.key}")
    private String apiKey;

    @Value("${trello.api.token}")
    private String token;

    @Value("${trello.api.baseURL}")
    private String trelloBaseURL;


    public void importCheckItemData() throws Exception{
        logger.info("CheckItemDataImportHandler importCheckItemData() method start");
        List<ChecklistEntity> checklistEntities = trelloChecklistRepository.findAll();
        for(ChecklistEntity checklistEntity : checklistEntities){
            logger.info("fetching checkItem for checklist {}",checklistEntity.getId());
            List<CheckItemModel> checkItems = importFromTrelloApi(checklistEntity.getId());
            if(CollectionUtils.isNotEmpty(checkItems)){
                trelloService.insertOrUpdateCheckItems(checkItems);
            }

        }
        logger.info("CheckItemDataImportHandler importCheckItemData() method end");
    }

    public List<CheckItemModel> importFromTrelloApi(String checklistId) throws Exception{
        String url = (trelloBaseURL + "/checklists/"+checklistId+"/checkItems?key=" + apiKey + "&token=" + token);
        logger.info("Fetching Checkitems for checklist: {}",url);
        try{
            ResponseEntity<CheckItemModel[]> responseEntity = restTemplate.getForEntity(url, CheckItemModel[].class);
            CheckItemModel[] checkItems = responseEntity.getBody();
            //logger.info("Fetching Checkitems Success. Received [{}]",checkItems.length);
            return Arrays.asList(checkItems);
        }catch (Exception ex){
            logger.error("Error fetching CheckItems of Checklist for {}: " + checklistId);
        }
       return null;
    }
}
