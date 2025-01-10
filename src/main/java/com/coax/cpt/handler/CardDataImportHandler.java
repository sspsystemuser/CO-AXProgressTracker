package com.coax.cpt.handler;

import com.coax.cpt.DataImportScheduler;
import com.coax.cpt.entity.BoardEntity;
import com.coax.cpt.model.CardModel;
import com.coax.cpt.repository.TrelloBoardRepository;
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
public class CardDataImportHandler {
    Logger logger = LoggerFactory.getLogger(DataImportScheduler.class);
    @Autowired
    TrelloService trelloService;

    @Autowired
    private TrelloBoardRepository trelloBoardRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${trello.api.key}")
    private String apiKey;

    @Value("${trello.api.token}")
    private String token;

    @Value("${trello.api.baseURL}")
    private String trelloBaseURL;

    public void importCardData() throws Exception{
        logger.info("CardDataImportHandler importCardData() method start");
        List<BoardEntity> boards = trelloBoardRepository.findAll();
        for(BoardEntity boardEntity : boards){
            logger.info("fetching cards for board {}",boardEntity.getId());
            List<CardModel> cards = importFromTrelloApi(boardEntity.getId());
            if(CollectionUtils.isNotEmpty(cards)){
                trelloService.insertOrUpdateCards(cards);
                trelloService.insertOrUpdateCardBadge(cards);
            }
        }
        logger.info("CardDataImportHandler importCardData() method end");
    }

    public List<CardModel> importFromTrelloApi(String boardId) throws Exception{
        String url = (trelloBaseURL + "/boards/"+boardId+"/cards?key=" + apiKey + "&token=" + token);
        logger.info("Fetching CardData: {}",url);
        try{
            ResponseEntity<CardModel[]> responseEntity = restTemplate.getForEntity(url, CardModel[].class);
            CardModel[] cards = responseEntity.getBody();
            //logger.info("Fetching CardData Success. Received [{}]",cards.length);
            return Arrays.asList(cards);
        }catch (Exception ex){
            logger.error("Error fetching Cards on Board for {}: " + boardId);
        }
        return null;
    }

}
