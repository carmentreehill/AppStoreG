package com.grability.test.appstoreg.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carmen Pérez Hernández on 16/05/16.
 */
public class Price {

    @SerializedName("attributes")
    private AttributePrice attributePrice;

    public AttributePrice getAttributePrice() {
        return attributePrice;
    }

    public void setAttributePrice(AttributePrice attributePrice) {
        this.attributePrice = attributePrice;
    }
}
