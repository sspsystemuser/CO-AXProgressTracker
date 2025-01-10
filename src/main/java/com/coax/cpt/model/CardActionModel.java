package com.coax.cpt.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CardActionModel {
    private String idMemberCreator;
    private CardActionDataModel data;
    private Date date;

    public String getIdMemberCreator() {
        return idMemberCreator;
    }

    public void setIdMemberCreator(String idMemberCreator) {
        this.idMemberCreator = idMemberCreator;
    }

    public CardActionDataModel getData() {
        return data;
    }

    public void setData(CardActionDataModel data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
