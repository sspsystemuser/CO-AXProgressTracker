package com.coax.cpt.handler;

import com.coax.cpt.DataImportScheduler;
import com.coax.cpt.entity.BoardEntity;
import com.coax.cpt.model.BoardListModel;
import com.coax.cpt.model.BoardMemberModel;
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
public class BoardMemberDataImportHandler {
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

    @Value("${trello.api.boardId}")
    private String boardId;

    public void importBoardMemberData() throws Exception{
        logger.info("BoardMemberDataImportHandler importBoardMemberData() method start");
        List<BoardEntity> boards = trelloBoardRepository.findAll();
        for(BoardEntity boardEntity : boards){
            logger.info("fetching members for board {}",boardEntity.getId());
            List<BoardMemberModel> boardMembers =  importFromTrelloApi(boardEntity.getId());
            if(CollectionUtils.isNotEmpty(boardMembers)){
                trelloService.insertOrUpdateBoardMembers(boardMembers);
            }
        }
        logger.info("BoardMemberDataImportHandler importBoardMemberData() method end");
    }

    public List<BoardMemberModel> importFromTrelloApi(String boardId) throws Exception{
        String url = (trelloBaseURL + "/boards/"+boardId+"/members?key=" + apiKey + "&token=" + token);
        logger.info("Fetching BoardMembers: {}",url);
        try{
            ResponseEntity<BoardMemberModel[]> responseEntity = restTemplate.getForEntity(url, BoardMemberModel[].class);
            BoardMemberModel[] boardMembers = responseEntity.getBody();
            //logger.info("Fetching Board Members Success. Received [{}]",boardMembers.length);
            return Arrays.asList(boardMembers);
        }catch (Exception ex){
            logger.error("Error fetching members on Board for {}: " + boardId);
        }
        return null;
    }
}
