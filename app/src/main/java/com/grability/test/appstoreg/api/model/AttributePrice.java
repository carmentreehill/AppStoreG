package com.grability.test.appstoreg.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carmen Pérez Hernández on 16/05/16.
 */
public class AttributePrice {

    @SerializedName("amount")
    private float amount;

    @SerializedName("currency")
    private String currency;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
