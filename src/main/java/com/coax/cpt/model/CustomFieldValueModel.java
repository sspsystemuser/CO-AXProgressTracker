package com.coax.cpt.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomFieldValueModel {
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
