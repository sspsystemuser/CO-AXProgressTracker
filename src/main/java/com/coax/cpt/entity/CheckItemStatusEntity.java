package com.coax.cpt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="checkitem_state_tab", schema = "public")
public class CheckItemStatusEntity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "status")
    private String state;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "state_update_date")
    private Date stateUpdateDate;

    @Column(name = "checklist_id")
    private String checklistId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Date getStateUpdateDate() {
        return stateUpdateDate;
    }

    public void setStateUpdateDate(Date stateUpdateDate) {
        this.stateUpdateDate = stateUpdateDate;
    }

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }
}
