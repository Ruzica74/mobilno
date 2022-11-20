package com.example.mymobapp.ui.settings;



import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.mymobapp.MainActivity;
import com.example.mymobapp.R;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Objects;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private Switch mySwitch;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private class Task extends AsyncTask<String, Void, String>{

        private WeakReference<SettingsFragment> activityReference;
        Task(SettingsFragment context){
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... strings) {
            String language;
            if(mySwitch.isChecked()){
                language = "sr";
                System.out.println("Srpskii: "+language);

            }else{
                language="en";
                System.out.println("eng: "+language);
            }
            if(getActivity()!=null) {
                SharedPreferences shPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = shPreferences.edit();
                editor.putString(MainActivity.SELECTED_LANGUAGE, language);
                editor.apply();
                System.out.println("Jeziiik: " + language);
                // sacuvamo promjene u konfiguraciji aplikacije
                Locale locale = new Locale(language);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getActivity().getBaseContext().getResources().updateConfiguration(config,
                        getActivity().getBaseContext().getResources().getDisplayMetrics());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activityReference.get().getActivity().recreate();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        System.out.println("Pocetak");
        mySwitch = getView().findViewById(R.id.switch1);
        SharedPreferences shPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String lang = shPreferences.getString(MainActivity.SELECTED_LANGUAGE, Locale.getDefault().getLanguage());
        //System.out.println(Locale.getDefault().getDisplayLanguage());
        if(lang.equals("en")){
            mySwitch.setChecked(false);
        }
        else if(lang.equals("sr")){
            mySwitch.setChecked(true);
        }
        System.out.println("on act created i lang: "+lang);
        SettingsFragment s= this;
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                Task task=new Task(s);
                task.execute();

            }
        });
        System.out.println("Kraj");
        try {
            SharedPreferences sh = getActivity().getSharedPreferences("My App", Context.MODE_PRIVATE);
            int number = sh.getInt("picture", 2);
            if(number==0) {
                RadioButton rb = getView().findViewById(R.id.radioButton1);
                rb.setChecked(true);
            }else if(number==1){
                RadioButton rb = getView().findViewById(R.id.radioButton2);
                rb.setChecked(true);
            }else if(number==2){
                RadioButton rb = getView().findViewById(R.id.radioButton3);
                rb.setChecked(true);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        RadioGroup  group= (RadioGroup) getView().findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                try {
                    View radioButton = radioGroup.findViewById(i);
                    int index = radioGroup.indexOfChild(radioButton);
                    System.out.println("Index radio button: " + index);
                    SharedPreferences sh = getActivity().getSharedPreferences("My App", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sh.edit();
                    myEdit.putInt("pictures", index);
                    myEdit.apply();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        ImageButton check = getView().findViewById(R.id.imageButtonCheck);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setImageResource(R.drawable.ic_check_yes);
                ImageButton x = getView().findViewById(R.id.imageButtonX);
                x.setImageResource(R.drawable.ic_x_gray);
                SharedPreferences sh = getActivity().getSharedPreferences("My App", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                myEdit.putBoolean("cache", true);
                myEdit.apply();
            }
        });

        ImageButton x = getView().findViewById(R.id.imageButtonX);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x.setImageResource(R.drawable.ic_x_orange);
                ImageButton c = getView().findViewById(R.id.imageButtonCheck);
                c.setImageResource(R.drawable.ic_check_no);
                SharedPreferences sh = getActivity().getSharedPreferences("My App", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                myEdit.putBoolean("cache", false);
                myEdit.apply();
            }
        });

        // TODO: Use the ViewModel
    }

}