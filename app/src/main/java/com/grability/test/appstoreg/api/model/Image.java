package com.grability.test.appstoreg.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carmen Pérez Hernández on 16/05/16.
 */
public class Image {
    @SerializedName("label")
    private String label;
    @SerializedName("attributes")
    private AttributeImage attribute;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AttributeImage getAttribute() {
        return attribute;
    }

    public void setAttribute(AttributeImage attribute) {
        this.attribute = attribute;
    }
}
