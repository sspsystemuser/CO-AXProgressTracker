package com.coax.cpt.model;

import org.springframework.stereotype.Component;

@Component
public class CardCustomFieldModel {
    private String idCustomField; //61375461a0e463167ce08c41
    private CustomFieldValueModel value;

    public String getIdCustomField() {
        return idCustomField;
    }

    public void setIdCustomField(String idCustomField) {
        this.idCustomField = idCustomField;
    }

    public CustomFieldValueModel getValue() {
        return value;
    }

    public void setValue(CustomFieldValueModel value) {
        this.value = value;
    }
}
