package com.example.mymobapp.ui.city;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymobapp.R;
import com.example.mymobapp.model.Article;
import com.example.mymobapp.model.City;
import com.example.mymobapp.ui.news.Adapter;
import com.example.mymobapp.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCity extends RecyclerView.Adapter<AdapterCity.MyViewHolder>{

    private List<City> cities;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public AdapterCity(List<City> cities, Context context) {
        this.cities = cities;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders,int position) {
        final MyViewHolder holder=holders;
        City model = cities.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        if(model.getName()!=null) {
            Picasso.get().load(model.getName()).into(holder.image);
        }else{
            holder.image.setImageResource(R.drawable.ic_image);
        }
        /*if(model.getTitle()!=null) {
            holder.title.setText(model.getTitle());
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl()));
                    context.startActivity(browserIntent);
                }
            });
        }
        if(model.getDescription()!=null) {
            holder.desc.setText(model.getDescription());
        }
        if(model.getPublishedAt()!=null) {
            holder.published_at.setText(Utils.DateFormat(model.getPublishedAt()));
            holder.time.setText("\u2022"+Utils.DateToTimeFormat(model.getPublishedAt()));
        }
        if(model.getAuthor()!=null) {
            holder.author.setText(model.getAuthor());
        }
        if(model.getSource()!=null) {
            holder.source.setText(model.getSource().getName());
        }*/
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, desc, author, published_at, source, time;
        ImageView image;
        OnItemClickListener onItemClickListener;


        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            published_at = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.img);

            this.onItemClickListener = onItemClickListener;
        }


        @Override
        public void onClick(View view) {
            if(onItemClickListener!=null) {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }

        }
    }
}
