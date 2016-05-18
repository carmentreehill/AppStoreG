package com.grability.test.appstoreg.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carmen Pérez Hernández on 15/05/16.
 */
public class Feed {


    @SerializedName("entry")
    private List<Entry> entry;

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }
}
