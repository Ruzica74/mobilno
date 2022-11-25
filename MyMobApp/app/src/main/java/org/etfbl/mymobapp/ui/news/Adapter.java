package org.etfbl.mymobapp.ui.news;




import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.etfbl.mymobapp.R;
import org.etfbl.mymobapp.model.Article;

import com.bumptech.glide.request.RequestOptions;
import org.etfbl.mymobapp.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private WebView webView;

    public Adapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder=holders;
        Article model = articles.get(position);
        //System.out.println("position:   "+position+ "  model: "+model.getTitle());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        //Glide.with(context).load(model.getUrlToImage()).into(holder.image);

        if(model.getUrlToImage()!=null) {
            Picasso.get().load(model.getUrlToImage()).into(holder.image);
        }else{
            holder.image.setImageResource(R.drawable.ic_image);
        }
        if(model.getTitle()!=null) {
            holder.title.setText(model.getTitle());
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sh = context.getSharedPreferences("My App", Context.MODE_PRIVATE);
                    Boolean news_cache = sh.getBoolean("cache", true);
                    //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl()));
                    //context.startActivity(browserIntent);
                    Intent activityIntent = new Intent(context, Cached_news.class);
                    activityIntent.putExtra("url", model.getUrl());
                    context.startActivity(activityIntent);
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
        }
    }



    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    private void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
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
