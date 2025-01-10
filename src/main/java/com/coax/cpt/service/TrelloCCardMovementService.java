package com.coax.cpt.service;

import com.coax.cpt.entity.CCardMovementEntity;
import com.coax.cpt.model.CCardMovementModel;
import com.coax.cpt.repository.TrelloCCardMovementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrelloCCardMovementService {
    Logger logger = LoggerFactory.getLogger(TrelloCCardMovementService.class);

    @Autowired
    private TrelloCCardMovementRepository trelloCCardMovementRepository;

    public void  insertOrUpdateCCardMovement(List<CCardMovementModel> cCardMovementModels){
        logger.info("TrelloService insertOrUpdateCCardMovement() method start");
        List<CCardMovementEntity> cCardMovementEntities = new ArrayList<>();
        for(CCardMovementModel cCardMovementModel : cCardMovementModels){

            Optional<CCardMovementEntity> cCardMovementEntityOpt = trelloCCardMovementRepository.findById(cCardMovementModel.getId());
            if(cCardMovementEntityOpt.isPresent()){
                CCardMovementEntity cCardMovementEntity = cCardMovementEntityOpt.get();
                cCardMovementEntity.setLastUpdatedDate(new Date());
                cCardMovementEntities.add(cCardMovementEntity);

            }else{
                CCardMovementEntity cCardMovementEntity = new CCardMovementEntity();
                if(cCardMovementModel.getData() !=null) {
                    cCardMovementEntity.setId(cCardMovementModel.getId());
                    cCardMovementEntity.setCardId(cCardMovementModel.getData().getCard().getId());
                    cCardMovementEntity.setFromListId(cCardMovementModel.getData().getListBefore().getId());
                    cCardMovementEntity.setFromListName(cCardMovementModel.getData().getListBefore().getName());
                    cCardMovementEntity.setToListId(cCardMovementModel.getData().getListAfter().getId());
                    cCardMovementEntity.setToListName(cCardMovementModel.getData().getListAfter().getName());
                    cCardMovementEntity.setMovedAt(cCardMovementModel.getDate());
                    cCardMovementEntity.setCreatedDate(new Date());
                    cCardMovementEntities.add(cCardMovementEntity);
                }
            }
        }
        trelloCCardMovementRepository.saveAll(cCardMovementEntities);
        logger.info("Total {} records movement inserted  for CCardEntity.",cCardMovementEntities.size());

    }
}
