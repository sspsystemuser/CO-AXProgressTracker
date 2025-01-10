package com.coax.cpt.model;

import org.springframework.stereotype.Component;

@Component
public class BadgesModel {
    private int checkItems;
    private int checkItemsChecked;

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

}
