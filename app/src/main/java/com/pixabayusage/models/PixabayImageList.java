package com.pixabayusage.models;

import java.util.List;

public class PixabayImageList {

    private final int total;
    private final int totalHits;
    private final List<PixabayImage> hits;

    public PixabayImageList(int total, int totalHits, List<PixabayImage> hits) {
        this.total = total;
        this.totalHits = totalHits;
        this.hits = hits;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public List<PixabayImage> getHits() {
        return hits;
    }

    public int getTotalOfPages() {
        return (int) Math.ceil(total / 20.0);
    }
}