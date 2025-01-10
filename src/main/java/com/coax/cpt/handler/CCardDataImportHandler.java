package com.coax.cpt.handler;

import com.coax.cpt.DataImportScheduler;
import com.coax.cpt.entity.CBoardEntity;
import com.coax.cpt.model.BoardListModel;
import com.coax.cpt.model.CardModel;
import com.coax.cpt.repository.TrelloCBoardRepository;
import com.coax.cpt.service.TrelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.collections4.CollectionUtils;
import java.util.Arrays;
import java.util.List;

@Component
public class CCardDataImportHandler {
    Logger logger = LoggerFactory.getLogger(DataImportScheduler.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TrelloCBoardRepository trelloCBoardRepository;

    @Autowired
    TrelloService trelloService;

    @Value("${trello.api.key}")
    private String apiKey;

    @Value("${trello.api.token}")
    private String token;

    @Value("${trello.api.baseURL}")
    private String trelloBaseURL;

    public void importCCardData() throws Exception{
        logger.info("CCardDataImportHandler importCCardData() method start");
        List<CBoardEntity> boards = trelloCBoardRepository.findAll();
        for(CBoardEntity boardEntity : boards){
            logger.info("fetching cards for board {}",boardEntity.getId());
            List<CardModel> cards = importFromTrelloApi(boardEntity.getId());
            if(CollectionUtils.isNotEmpty(cards)) {
                trelloService.insertOrUpdateCCards(cards);
            }
        }
        logger.info("CCardDataImportHandler importCCardData() method end");
    }

    public List<CardModel> importFromTrelloApi(String boardId) throws Exception{
        String url = (trelloBaseURL + "/boards/"+boardId+"/cards?key=" + apiKey + "&token=" + token);
        logger.info("Fetching CardData: {}",url);
        try {
            ResponseEntity<CardModel[]> responseEntity = restTemplate.getForEntity(url, CardModel[].class);
            CardModel[] cards = responseEntity.getBody();
            return Arrays.asList(cards);
        } catch (HttpClientErrorException e) {
            // Log the error and return null if the board is private or access is denied
            if (e.getStatusCode().value() == 401 || e.getStatusCode().value() == 403) {
                logger.error("Access denied to board: " + boardId);
            }
        }
        return null;
    }
}
