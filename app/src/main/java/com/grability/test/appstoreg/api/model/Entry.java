package com.grability.test.appstoreg.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carmen Pérez Hernández on 15/05/16.
 */
public class Entry {
    @SerializedName("category")
    private Category category;

    @SerializedName("im:name")
    private ImName name;

    @SerializedName("im:image")
    private List<Image> image;

    @SerializedName("summary")
    private Summary summary;

    @SerializedName("im:price")
    private Price price;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ImName getName() {
        return name;
    }

    public void setName(ImName name) {
        this.name = name;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
