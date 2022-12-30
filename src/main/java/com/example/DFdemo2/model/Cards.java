package com.example.DFdemo2.model;

public class Cards {

    private String header;
    private String description;
    private String imageUrl;
    private String link;

    public Cards(String header, String description, String imageUrl, String link) {
        this.header = header;
        this.description = description;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
