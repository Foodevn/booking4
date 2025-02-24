package com.example.booking4.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Cast  implements Parcelable {
    private String PicUrl;
    private String Actor;

    public Cast() {
    }

    public Cast(String Actor,String PicUrl) {
        this.PicUrl = PicUrl;
        this.Actor = Actor;
    }

    protected Cast(Parcel in) {
        PicUrl = in.readString();
        Actor = in.readString();
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String PicUrl) {
        this.PicUrl = PicUrl;
    }

    public String getActor() {
        return Actor;
    }

    public void setActor(String Actor) {
        this.Actor = Actor;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(PicUrl);
        parcel.writeString(Actor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };
}
