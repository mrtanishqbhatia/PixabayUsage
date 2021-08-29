package com.pixabayusage.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pixabayusage.R;
import com.pixabayusage.activities.ImageDetailsActivity;
import com.pixabayusage.interfaces.ImageClickListener;
import com.pixabayusage.models.Image;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Image> pixabayImageList;
    private Context context;

    public RecyclerViewAdapter(List<Image> pixabayImageList) {
        this.pixabayImageList = pixabayImageList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.textView.setText(pixabayImageList.get(position).getTags());
        holder.likes.setText(pixabayImageList.get(position).getLikes());

        Glide.with(holder.itemView)
                .load(pixabayImageList.get(position).getWebformatURL())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.imageView);

        holder.setImageClickListener((v, pos) -> {
            Intent i = new Intent(context, ImageDetailsActivity.class);
            i.putExtra("url", pixabayImageList.get(pos).getWebformatURL());
            i.putExtra("tags", pixabayImageList.get(pos).getTags());
            i.putExtra("views", pixabayImageList.get(pos).getViews());
            i.putExtra("downloads", pixabayImageList.get(pos).getDownloads());
            i.putExtra("likes", pixabayImageList.get(pos).getLikes());
            i.putExtra("comments", pixabayImageList.get(pos).getComments());
            i.putExtra("user", pixabayImageList.get(pos).getUser());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return pixabayImageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView, likes;
        private ImageClickListener imageClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_image);
            textView = itemView.findViewById(R.id.title);
            likes = itemView.findViewById(R.id.likes);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.imageClickListener.onImageChoose(v, getLayoutPosition());
        }

        public void setImageClickListener(ImageClickListener ic) {
            this.imageClickListener = ic;
        }
    }
}
