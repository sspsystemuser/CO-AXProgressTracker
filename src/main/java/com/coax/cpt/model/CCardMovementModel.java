package com.coax.cpt.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CCardMovementModel {
    private String id;
    private String idMemberCreator;
    private CCardMovementDataModel data;
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMemberCreator() {
        return idMemberCreator;
    }

    public void setIdMemberCreator(String idMemberCreator) {
        this.idMemberCreator = idMemberCreator;
    }

    public CCardMovementDataModel getData() {
        return data;
    }

    public void setData(CCardMovementDataModel data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
