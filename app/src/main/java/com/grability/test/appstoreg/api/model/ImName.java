package com.grability.test.appstoreg.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by carmen on 16/05/16.
 */
public class ImName {

    @SerializedName("label")
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
