package com.coax.cpt.model;

import org.springframework.stereotype.Component;

@Component
public class CCardMovementDataModel {
    private CardModel card;
    private CCardMovementListBeforeModel listBefore;
    private CCardMovementListAfterModel listAfter;

    public CardModel getCard() {
        return card;
    }

    public void setCard(CardModel card) {
        this.card = card;
    }

    public CCardMovementListBeforeModel getListBefore() {
        return listBefore;
    }

    public void setListBefore(CCardMovementListBeforeModel listBefore) {
        this.listBefore = listBefore;
    }

    public CCardMovementListAfterModel getListAfter() {
        return listAfter;
    }

    public void setListAfter(CCardMovementListAfterModel listAfter) {
        this.listAfter = listAfter;
    }
}
