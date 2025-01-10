package com.coax.cpt.service;

import com.coax.cpt.entity.CheckitemEntity;
import com.coax.cpt.model.CheckItemModel;
import com.coax.cpt.repository.TrelloCheckitemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrelloCheckItemService {
    Logger logger = LoggerFactory.getLogger(TrelloCheckItemService.class);

    @Autowired
    private TrelloCheckitemRepository trelloCheckitemRepository;

    public void insertOrUpdateCheckItems(List<CheckItemModel> checkItemModels){
        logger.info("TrelloService insertOrUpdateCheckItems() method start");
        List<CheckitemEntity> checkitemEntities = new ArrayList<>();
        for(CheckItemModel checkItemModel : checkItemModels){
            Optional<CheckitemEntity> checkItemEntityOpt = trelloCheckitemRepository.findById(checkItemModel.getId());
            if(checkItemEntityOpt.isPresent()){
                CheckitemEntity checkitemEntity = checkItemEntityOpt.get();
                checkitemEntity.setName(checkItemModel.getName());
                checkitemEntity.setStatus(checkItemModel.getState());
                checkitemEntity.setLastUpdatedDate(new Date());
                checkitemEntities.add(checkitemEntity);
            }else{
                CheckitemEntity checkItemEntity = new CheckitemEntity();
                checkItemEntity.setId(checkItemModel.getId());
                checkItemEntity.setName(checkItemModel.getName());
                checkItemEntity.setChecklistId(checkItemModel.getIdChecklist());
                checkItemEntity.setStatus(checkItemModel.getState());
                checkItemEntity.setCreatedDate(new Date());
                checkitemEntities.add(checkItemEntity);
            }

        }
        trelloCheckitemRepository.saveAll(checkitemEntities);
        logger.info("Total {} records inserted for CheckItems.",checkitemEntities.size());
    }
}
