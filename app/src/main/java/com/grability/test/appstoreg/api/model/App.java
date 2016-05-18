package com.grability.test.appstoreg.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carmen Pérez Hernández on 14/05/16.
 */
public class App {

    @SerializedName("feed")
    private Feed feed;


    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

}
