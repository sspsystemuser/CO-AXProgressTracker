package com.coax.cpt.model;

import org.springframework.stereotype.Component;

@Component
public class CardModel {
    private String id;
    private String name;
    private String idBoard;
    private String idList;
    private BadgesModel badges;


    public CardModel() {
    }

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

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public BadgesModel getBadges() {
        return badges;
    }

    public void setBadges(BadgesModel badges) {
        this.badges = badges;
    }
}
