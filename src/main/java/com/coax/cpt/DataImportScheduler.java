package com.coax.cpt;

import com.coax.cpt.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataImportScheduler {
    Logger logger = LoggerFactory.getLogger(DataImportScheduler.class);

    @Autowired
    CardDataImportHandler cardDataImportHandler;

    @Autowired
    ChecklistDataImportHandler checklistDataImportHandler;

    @Autowired
    CheckItemDataImportHandler checkItemDataImportHandler;

    @Autowired
    CheckItemStatusDataImportHandler checkItemStatusDataImportHandler;

    @Autowired
    BoardListDataImportHandler boardListDataImportHandler;

    @Autowired
    BoardMemberDataImportHandler boardMemberDataImportHandler;

    @Autowired
    CardDetailsDataImportHandler cardDetailsDataImportHandler;

    @Autowired
    CBoardListDataImportHandler cBoardListDataImportHandler;

    @Autowired
    CCardDataImportHandler cCardDataImportHandler;

    @Autowired
    CCardMovementDataImportHandler cCardMovementDataImportHandler;


    // Run the task every 30 minute
    @Scheduled(fixedRate = 1800000)
    public void startDataImportScheduler() {
        logger.info("Start Scheduler");
        try{
            boardMemberDataImportHandler.importBoardMemberData();
            boardListDataImportHandler.importBoardListData();
            cardDataImportHandler.importCardData();
            cardDetailsDataImportHandler.importCardDetailsStatusData();
           /* checklistDataImportHandler.importChecklistData();
            checkItemDataImportHandler.importCheckItemData();*/
            checkItemStatusDataImportHandler.importCardStatusData();
            cBoardListDataImportHandler.importCBoardListData();
            cCardDataImportHandler.importCCardData();
            cCardMovementDataImportHandler.importCCardMovementData();

            logger.info("Scheduler run Successfully!");
        }catch (Exception e){
            logger.error("Error occurred while startDataImportScheduler start", e);
        }
    }

    /*@Value("${myapp.fixedRate:300000}")
    private long fixedRate;

    @Scheduled(fixedRateString = "${myapp.fixedRate}")
    public void dynamicScheduledTask() {
        try{

        }catch (Exception e){
            System.err.println("Error occurred in scheduled task: " + e.getMessage());
        }
        System.out.println("Dynamically configured task running at a fixed rate of: " + fixedRate);
    }*/
}
