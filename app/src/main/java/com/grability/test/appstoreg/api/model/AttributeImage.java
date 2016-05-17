package com.grability.test.appstoreg.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by carmen on 16/05/16.
 */
public class AttributeImage {
    @SerializedName("height")
    private long height;

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }
}
