package com.coax.cpt.handler;

import com.coax.cpt.DataImportScheduler;
import com.coax.cpt.entity.CCardEntity;
import com.coax.cpt.entity.CardEntity;
import com.coax.cpt.model.CCardMovementModel;
import com.coax.cpt.model.CardActionModel;
import com.coax.cpt.repository.TrelloCCardRepository;
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
public class CCardMovementDataImportHandler {
    Logger logger = LoggerFactory.getLogger(DataImportScheduler.class);
    @Autowired
    TrelloService trelloService;

    @Autowired
    TrelloCCardRepository trelloCCardRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${trello.api.key}")
    private String apiKey;

    @Value("${trello.api.token}")
    private String token;

    @Value("${trello.api.baseURL}")
    private String trelloBaseURL;

    public void importCCardMovementData() throws Exception{
        logger.info("CCardMovementDataImportHandler importCCardMovementData() method start");
        List<CCardEntity> cCardEntities = trelloCCardRepository.findAll();
        for(CCardEntity cCardEntity : cCardEntities){
            logger.info("fetching actions for card {}",cCardEntity.getId());
            List<CCardMovementModel> cCardMovementModels = importFromTrelloApi(cCardEntity.getId());
            if(CollectionUtils.isNotEmpty(cCardMovementModels)) {
                trelloService.insertOrUpdateCCardMovement(cCardMovementModels);
            }

        }
        logger.info("CCardMovementDataImportHandler importCCardMovementData() method end");
    }

    public List<CCardMovementModel> importFromTrelloApi(String cardId) throws Exception{
        String url = (trelloBaseURL + "/cards/"+cardId+"/actions?key=" + apiKey + "&token=" + token +"&filter=updateCard:idList");
        logger.info("Fetching CCardMovement: {}",url);
        try{
            ResponseEntity<CCardMovementModel[]> responseEntity = restTemplate.getForEntity(url, CCardMovementModel[].class);
            CCardMovementModel[] cardMovements = responseEntity.getBody();
            //logger.info("Total CardMovement Received [{}]",cardMovements.length);
            return Arrays.asList(cardMovements);
        }catch (Exception ex){
            logger.error("Error fetching movement of CCards on CCard for {}: " + cardId);
        }
        return null;
    }
}
