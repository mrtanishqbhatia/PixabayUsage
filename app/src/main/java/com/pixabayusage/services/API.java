package com.pixabayusage.services;

import com.pixabayusage.models.PixabayImageList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("/api/")
    Call<PixabayImageList> getImages(
            @Query("key") String key,
            @Query("q") String query,
            @Query("page") int page,
            @Query("per_page") int perPage);
}
