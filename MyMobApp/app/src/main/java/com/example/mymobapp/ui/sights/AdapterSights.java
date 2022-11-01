package com.example.mymobapp.ui.sights;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymobapp.R;
import com.example.mymobapp.database.RepositorySights;
import com.example.mymobapp.model.City;
import com.example.mymobapp.model.Sight;
import com.example.mymobapp.ui.city.AdapterCity;
import com.example.mymobapp.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterSights extends RecyclerView.Adapter<AdapterSights.MyViewHolder>{

    private List<Sight> sights;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private Application application;

    public AdapterSights(List<Sight> sights, Context context, Application a){
        this.sights=sights;
        this.context=context;
        this.application=a;
    }

    public void setSights(List<Sight> sights) {
        this.sights = sights;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sight, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSights.MyViewHolder holders, int position) {
        final MyViewHolder holder=holders;
        Sight model = sights.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        //SharedPreferences shPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        //String lang = shPreferences.getString(MainActivity.SELECTED_LANGUAGE, Locale.getDefault().getLanguage());

        String lanf= Locale.getDefault().getLanguage();
        //System.out.println("jeziik: "+lanf+" model: "+model);

        if(lanf.equals("en")) {
            holder.name.setText(model.getNameEn());
            System.out.println("uslo en sight");
        }else{
            holder.name.setText(model.getName());
            System.out.println("uslo sr sight");
        }
        if(model.getFavourit()){
            Drawable res = context.getResources().getDrawable(R.drawable.ic_star_orange);
            holder.image.setImageDrawable(res);
            holder.image.setTag("orange");
        }
        else{
            Drawable res = context.getResources().getDrawable(R.drawable.ic_star_gray);
            holder.image.setImageDrawable(res);
            holder.image.setTag("gray");
        }
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl()));
                //context.startActivity(browserIntent);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable image_now = holder.image.getDrawable();
                ImageView imageView = (ImageView) v;
                System.out.println("uslo na klik frame");
                RepositorySights r = new RepositorySights(application);
                Drawable resource;
                String color="";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    System.out.println("Image now: "+String.valueOf(imageView.getTag()));
                    color=String.valueOf(imageView.getTag());
                }
                if(color.equals("gray")){
                    resource = context.getResources().getDrawable(R.drawable.ic_star_orange);
                    model.setFavourit(true);
                    holder.image.setTag("orange");
                    System.out.println("orange: "+model.getName());
                    new UpdateTask(r, model).execute();
                } else {
                    resource = context.getResources().getDrawable(R.drawable.ic_star_gray);
                    model.setFavourit(false);
                    holder.image.setTag("gray");
                    System.out.println("gray: "+model.getName());
                    new UpdateTask(r, model).execute();
                }
                new Thread(new Runnable() {
                    public void run() {
                        holder.image.setImageDrawable(resource);
                    }}).start();
            }
        });

    }

    private class UpdateTask extends AsyncTask<Void, Void, Boolean> {

        private RepositorySights repositorySights;
        private Sight s;

        // only retain a weak reference to the activity
        UpdateTask(RepositorySights r, Sight s) {
            super();
            repositorySights=r;
            this.s=s;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            repositorySights.update(s);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            List<Sight> lista = repositorySights.getAll();
            for (Sight jedan : lista) {
                if(jedan.getName().equals(s.getName())){
                    System.out.println(jedan.getName()+" izmjenjeni favorit: "+jedan.getFavourit());
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return sights.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    private void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        ImageView image;
        OnItemClickListener onItemClickListener;
        FrameLayout frame;


        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.sight);
            image = itemView.findViewById(R.id.star);
            frame = itemView.findViewById(R.id.layoutStar);

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
