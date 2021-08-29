package com.pixabayusage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pixabayusage.R;
import com.pixabayusage.adapters.RecyclerViewAdapter;
import com.pixabayusage.models.PixabayImage;
import com.pixabayusage.models.PixabayImageList;
import com.pixabayusage.services.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<PixabayImage> imageList;
    private ProgressBar progressBar;
    private TextView noImages;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRecyclerView();
        addListeners();
        retrieveImages();
    }

    private void addListeners() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterImages(editable.toString());
            }
        });
    }

    private void filterImages(String searchString) {
        List<PixabayImage> searchedImageList = new ArrayList<>();
        for (PixabayImage pixabayImage: imageList) {
            if (pixabayImage.getTags().toLowerCase(Locale.ROOT).contains(searchString.toLowerCase(Locale.ROOT))) {
                searchedImageList.add(pixabayImage);
            }
        }
        recyclerViewAdapter.displayFilteredImages(searchedImageList);
    }

    private void saveImagesResponse(PixabayImageList body) {
        progressBar.setVisibility(View.GONE);
        int quantity = imageList.size();
        imageList.addAll(body.getHits());
        recyclerViewAdapter.notifyItemRangeInserted(quantity, quantity + 20);
        if (imageList.isEmpty()) noImages.setVisibility(View.VISIBLE);
        else noImages.setVisibility(View.GONE);
    }

    private void retrieveImages() {
        Service.generatePixabayService().getImages(
                getString(R.string.API_KEY), "art", 1, 20)
                .enqueue(new Callback<PixabayImageList>() {
                    @Override
                    public void onResponse(Call<PixabayImageList> call, Response<PixabayImageList> response) {
                        if (response.isSuccessful())
                            saveImagesResponse(response.body());
                        else progressBar.setVisibility(View.GONE);
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
        progressBar = findViewById(R.id.imageProgressBar);
        noImages = findViewById(R.id.noImagesTextView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
    }
}