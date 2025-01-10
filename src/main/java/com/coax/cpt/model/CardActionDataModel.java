package com.coax.cpt.model;

import org.springframework.stereotype.Component;

@Component
public class CardActionDataModel {

    private CheckItemModel checkItem;
    private CheckListModel checklist;

    public CheckItemModel getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(CheckItemModel checkItem) {
        this.checkItem = checkItem;
    }

    public CheckListModel getChecklist() {
        return checklist;
    }

    public void setChecklist(CheckListModel checklist) {
        this.checklist = checklist;
    }
}
