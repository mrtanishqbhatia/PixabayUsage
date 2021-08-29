package com.pixabayusage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pixabayusage.R;
import com.pixabayusage.adapters.RecyclerViewAdapter;
import com.pixabayusage.models.PixabayImage;
import com.pixabayusage.models.PixabayImageList;
import com.pixabayusage.services.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<PixabayImage> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRecyclerView();
        retrieveImages();
    }

    private void saveImagesResponse(PixabayImageList body) {
        int quantity = imageList.size();
        imageList.addAll(body.getHits());
        recyclerViewAdapter.notifyItemRangeInserted(quantity, quantity + 20);
    }

    private void retrieveImages() {
        Service.generatePixabayService().getImages(
                getString(R.string.API_KEY), "art", 1, 20)
                .enqueue(new Callback<PixabayImageList>() {
                    @Override
                    public void onResponse(Call<PixabayImageList> call, Response<PixabayImageList> response) {
                        if (response.isSuccessful())
                            saveImagesResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<PixabayImageList> call, Throwable t) {

                    }
                });
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        imageList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(imageList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
}