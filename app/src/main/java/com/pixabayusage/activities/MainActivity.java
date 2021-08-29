package com.pixabayusage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pixabayusage.R;
import com.pixabayusage.adapters.RecyclerViewAdapter;
import com.pixabayusage.models.Image;
import com.pixabayusage.models.ImageList;
import com.pixabayusage.services.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Image> imageList;
    private ProgressBar progressBar;
    private TextView noImages;
    private EditText searchEditText;
    private String searchFor = "random";
    private final long delay = 1000;
    private long last_text_edit = 0;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRecyclerView();
        addListeners();
        retrieveImages(searchFor);
    }

    private final Runnable searchInputCheck = () -> {
        if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
            searchFor = searchEditText.getText().toString();
            retrieveImages(searchFor);
        }
    };

    private void addListeners() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(searchInputCheck);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(searchInputCheck, delay);
                }
            }
        });
    }

    private void saveImagesResponse(ImageList body) {
        progressBar.setVisibility(View.GONE);
        imageList.clear();
        imageList.addAll(body.getHits());
        recyclerViewAdapter.notifyDataSetChanged();
        if (imageList.isEmpty()) noImages.setVisibility(View.VISIBLE);
        else noImages.setVisibility(View.GONE);
    }

    private void retrieveImages(String searchFor) {
        Service.generatePixabayService().getImages(
                getString(R.string.API_KEY), searchFor, 1, 20)
                .enqueue(new Callback<ImageList>() {
                    @Override
                    public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                        Log.d("Response", response.toString());
                        if (response.isSuccessful())
                            saveImagesResponse(response.body());
                        else progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ImageList> call, Throwable t) {

                    }
                });
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
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