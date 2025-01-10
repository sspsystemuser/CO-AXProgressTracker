package com.coax.cpt.handler;

import com.coax.cpt.DataImportScheduler;
import com.coax.cpt.entity.CBoardEntity;
import com.coax.cpt.model.BoardListModel;
import com.coax.cpt.repository.TrelloCBoardRepository;
import com.coax.cpt.service.TrelloService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class CBoardListDataImportHandler {

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

    public void importCBoardListData() throws Exception{
        logger.info("CBoardListDataImportHandler importCBoardListData() method start");
        List<CBoardEntity> boards = trelloCBoardRepository.findAll();
        for(CBoardEntity boardEntity : boards){
            logger.info("fetching Lists for board {}",boardEntity.getId());
            List<BoardListModel> boardLists =  importFromTrelloApi(boardEntity.getId());
            if(CollectionUtils.isNotEmpty(boardLists)) {
                trelloService.insertOrUpdateCBoardLists(boardLists, boardEntity.getId());
            }
        }
        logger.info("CBoardListDataImportHandler importCBoardListData() method end");
    }

    public List<BoardListModel> importFromTrelloApi(String boardId) throws Exception{
        String url = (trelloBaseURL + "/boards/"+boardId+"/lists?key=" + apiKey + "&token=" + token);
        logger.info("Fetching CBoardLists: {}",url);
        try {
            ResponseEntity<BoardListModel[]> responseEntity = restTemplate.getForEntity(url, BoardListModel[].class);
            BoardListModel[] boardLists = responseEntity.getBody();
            return Arrays.asList(boardLists);
        } catch (HttpClientErrorException e) {
            // Log the error and return null if the board is private or access is denied
            if (e.getStatusCode().value() == 401 || e.getStatusCode().value() == 403) {
                logger.error("Access denied to board: " + boardId);
            }
        }
        return null;
    }
}
