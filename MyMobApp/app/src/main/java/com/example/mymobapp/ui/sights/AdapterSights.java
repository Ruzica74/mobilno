package com.example.mymobapp.ui.sights;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymobapp.R;
import com.example.mymobapp.model.City;
import com.example.mymobapp.model.Sight;
import com.example.mymobapp.ui.city.AdapterCity;
import com.example.mymobapp.util.Utils;

import java.util.List;
import java.util.Locale;

public class AdapterSights extends RecyclerView.Adapter<AdapterSights.MyViewHolder>{

    private List<Sight> sights;
    private Context context;
    private OnItemClickListener onItemClickListener;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false);
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
            System.out.println("uslo en");
        }else{
            holder.name.setText(model.getName());
            System.out.println("uslo sr");
        }
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl()));
                //context.startActivity(browserIntent);
            }
        });
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
        //ImageView image;
        OnItemClickListener onItemClickListener;


        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.sight);
            //image = itemView.findViewById(R.id.img);

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
