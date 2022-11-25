package org.etfbl.mymobapp.ui.city;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import org.etfbl.mymobapp.R;
import org.etfbl.mymobapp.model.City;
import org.etfbl.mymobapp.ui.activity.CityActivity;
import org.etfbl.mymobapp.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.List;
import java.util.Locale;

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

        //SharedPreferences shPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        //String lang = shPreferences.getString(MainActivity.SELECTED_LANGUAGE, Locale.getDefault().getLanguage());

        String lanf=Locale.getDefault().getLanguage();
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
                try{
                    Intent activityIntent = new Intent(context, CityActivity.class);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String json = ow.writeValueAsString(model);
                    activityIntent.putExtra("model", json);
                    activityIntent.putExtra("language", lanf);
                    context.startActivity(activityIntent);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public int getItemCount() {
        return cities.size();
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
            name = itemView.findViewById(R.id.city_name);
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
