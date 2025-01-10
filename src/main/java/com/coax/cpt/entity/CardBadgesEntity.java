package com.coax.cpt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="card_badges_tab", schema = "public")
public class CardBadgesEntity implements Serializable{

    @Id
    @Column(name = "card_id")
    private String card_id;

    @Column(name = "checkitems")
    private int checkItems;

    @Column(name="checkitems_checked")
    private int checkItemsChecked;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_update_date")
    private Date lastUpdatedDate;

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
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
