package com.coax.cpt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="c_card_movement_tab", schema = "public")
public class CCardMovementEntity implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "from_list_id")
    private String fromListId;

    @Column(name = "from_list_name")
    private String fromListName;

    @Column(name = "to_list_id")
    private String toListId;

    @Column(name = "to_list_name")
    private String toListName;

    @Column(name = "moved_date")
    private Date movedAt;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_update_date")
    private Date lastUpdatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getFromListId() {
        return fromListId;
    }

    public void setFromListId(String fromListId) {
        this.fromListId = fromListId;
    }

    public String getFromListName() {
        return fromListName;
    }

    public void setFromListName(String fromListName) {
        this.fromListName = fromListName;
    }

    public String getToListId() {
        return toListId;
    }

    public void setToListId(String toListId) {
        this.toListId = toListId;
    }

    public String getToListName() {
        return toListName;
    }

    public void setToListName(String toListName) {
        this.toListName = toListName;
    }

    public Date getMovedAt() {
        return movedAt;
    }

    public void setMovedAt(Date movedAt) {
        this.movedAt = movedAt;
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
