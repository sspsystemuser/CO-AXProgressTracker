package com.coax.cpt.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Table(name="card_tab", schema = "public")
public class CardEntity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "board_list_id")
    private String boardListId;

    @Column(name = "total_checkitems")
    private int checkItems;

    @Column(name = "completed_checkitems")
    private int checkItemsChecked;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_update_date")
    private Date lastUpdatedDate;

    public CardEntity() {
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

    public String getBoardListId() {
        return boardListId;
    }

    public void setBoardListId(String boardListId) {
        this.boardListId = boardListId;
    }

    public int getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(int checkItems) {
        this.checkItems = checkItems;
    }

    public int getCheckItemsChecked() {
        return checkItemsChecked;
    }

    public void setCheckItemsChecked(int checkItemsChecked) {
        this.checkItemsChecked = checkItemsChecked;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
