package com.coax.cpt.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardDetailsModel {
    private String id;
    private BadgesModel badges;
    private String idBoard;
    private String idList;
    private String name;
    private List<CheckListModel> checklists;
    private List<CardCustomFieldModel> customFieldItems;
    private List<CardActionModel> actions;
    private List<CardAttachmentModel> attachments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BadgesModel getBadges() {
        return badges;
    }

    public void setBadges(BadgesModel badges) {
        this.badges = badges;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CheckListModel> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<CheckListModel> checklists) {
        this.checklists = checklists;
    }

    public List<CardCustomFieldModel> getCustomFieldItems() {
        return customFieldItems;
    }

    public void setCustomFieldItems(List<CardCustomFieldModel> customFieldItems) {
        this.customFieldItems = customFieldItems;
    }

    public List<CardActionModel> getActions() {
        return actions;
    }

    public void setActions(List<CardActionModel> actions) {
        this.actions = actions;
    }

    public List<CardAttachmentModel> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CardAttachmentModel> attachments) {
        this.attachments = attachments;
    }
}
