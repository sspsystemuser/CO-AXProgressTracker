package com.coax.cpt.handler;


import com.coax.cpt.DataImportScheduler;
import com.coax.cpt.entity.BoardEntity;
import com.coax.cpt.entity.CardEntity;
import com.coax.cpt.model.BoardListModel;
import com.coax.cpt.model.CardActionModel;
import com.coax.cpt.model.CardModel;
import com.coax.cpt.model.CheckListModel;
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
public class BoardListDataImportHandler {
    Logger logger = LoggerFactory.getLogger(DataImportScheduler.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TrelloBoardRepository trelloBoardRepository;

    @Autowired
    TrelloService trelloService;

    @Value("${trello.api.key}")
    private String apiKey;

    @Value("${trello.api.token}")
    private String token;

    @Value("${trello.api.baseURL}")
    private String trelloBaseURL;


    public void importBoardListData() throws Exception{
        logger.info("BoardListDataImportHandler importBoardListData() method start");
        List<BoardEntity> boards = trelloBoardRepository.findAll();
        for(BoardEntity boardEntity : boards){
            logger.info("fetching Lists for board {}",boardEntity.getId());
            List<BoardListModel> boardLists =  importFromTrelloApi(boardEntity.getId());
            if(CollectionUtils.isNotEmpty(boardLists)){
                trelloService.insertOrUpdateBoardLists(boardLists);
            }
        }
        logger.info("BoardListDataImportHandler importBoardListData() method end");
    }

    public List<BoardListModel> importFromTrelloApi(String boardId) throws Exception{
        String url = (trelloBaseURL + "/boards/"+boardId+"/lists?key=" + apiKey + "&token=" + token);
        logger.info("Fetching BoardLists: {}",url);
        try{
            ResponseEntity<BoardListModel[]> responseEntity = restTemplate.getForEntity(url, BoardListModel[].class);
            BoardListModel[] boardLists = responseEntity.getBody();
            return Arrays.asList(boardLists);
        }catch (Exception ex){
            logger.error("Error fetching List on Board for {}: " + boardId);
        }
        return null;
    }
}
