package com.coax.cpt.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckListModel {
    private String id;
    private String name;
    private String idBoard;
    private String idCard;
    private List<CheckItemModel> checkItems;
    String customFieldItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdBoard() {
        return idBoard;
    }

    public void setIdBoard(String idBoard) {
        this.idBoard = idBoard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public List<CheckItemModel> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<CheckItemModel> checkItemResponseEntities) {
        this.checkItems = checkItemResponseEntities;
    }

}
