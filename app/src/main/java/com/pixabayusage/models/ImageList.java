package com.pixabayusage.models;

import java.util.List;

public class ImageList {

    private final List<Image> hits;

    public ImageList(List<Image> hits) {
        this.hits = hits;
    }

    public List<Image> getHits() {
        return hits;
    }
}