package com.grability.test.appstoreg.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carmen Pérez Hernández on 15/05/16.
 */
public class Category {
    @SerializedName("attributes")
    private Attribute attributes;

    public Attribute getAttributes() {
        return attributes;
    }

    public void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }
}
