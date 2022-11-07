package com.example.mymobapp.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.mymobapp.database.Constants;

import java.io.Serializable;

@Entity(tableName = Constants.TABLE_NAME_CITY)
public class City implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String nameEn;
    String textEn;
    String textSr;
    String picture1;
    String picture2;
    String picture3;
    String video;
    String geoDuz;
    String geoSir;

    @Ignore
    public City(){

    }

    public City(String name, String nameEn, String textEn, String textSr, String picture1, String picture2, String picture3, String video, String geoDuz, String geoSir) {
        this.name = name;
        this.nameEn = nameEn;
        this.textEn = textEn;
        this.textSr = textSr;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
        this.video = video;
        this.geoDuz = geoDuz;
        this.geoSir = geoSir;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public String getTextSr() {
        return textSr;
    }

    public void setTextSr(String textSr) {
        this.textSr = textSr;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getGeoDuz() {
        return geoDuz;
    }

    public void setGeoDuz(String geoDuz) {
        this.geoDuz = geoDuz;
    }

    public String getGeoSir() {
        return geoSir;
    }

    public void setGeoSir(String geoSir) {
        this.geoSir = geoSir;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", textEn='" + textEn + '\'' +
                ", textSr='" + textSr + '\'' +
                ", picture1='" + picture1 + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", picture3='" + picture3 + '\'' +
                ", video='" + video + '\'' +
                ", geoDuz='" + geoDuz + '\'' +
                ", geoSir='" + geoSir + '\'' +
                '}';
    }
}
