package com.example.booking4.Models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Film implements Parcelable {
    private String Title;
    private String Description;
    private String Poster;
    private String Time;
    private String Trailer;
    private int Imdb;
    private int Year;
    private double Price;
    private ArrayList<String> Genre;
    private ArrayList<Cast> Casts;

    public Film() {
        Casts = new ArrayList<>();
        Genre = new ArrayList<>();

    }

    public Film(ArrayList<Cast> Casts, String Description, ArrayList<String> Genre, int Imdb, String Poster, String Time, String Title
            , String Trailer, int Year, double Price) {
        this.Title = Title;
        this.Description = Description;
        this.Poster = Poster;
        this.Time = Time;
        this.Trailer = Trailer;
        this.Imdb = Imdb;
        this.Year = Year;
        this.Price = Price;
        this.Genre = Genre;
        this.Casts = Casts;
    }

    protected Film(Parcel in) {
        Title = in.readString();
        Description = in.readString();
        Poster = in.readString();
        Time = in.readString();
        Trailer = in.readString();
        Imdb = in.readInt();
        Year = in.readInt();
        Price = in.readDouble();
        Genre = in.createStringArrayList();
        Casts = in.createTypedArrayList(Cast.CREATOR);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String Poster) {
        this.Poster = Poster;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getTrailer() {
        return Trailer;
    }

    public void setTrailer(String Trailer) {
        this.Trailer = Trailer;
    }

    public int getImdb() {
        return Imdb;
    }

    public void setImdb(int Imdb) {
        this.Imdb = Imdb;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public ArrayList<String> getGenre() {
        return Genre;
    }

    public void setGenre(ArrayList<String> Genre) {
        this.Genre = Genre;
    }

    public ArrayList<Cast> getCasts() {
        return Casts;
    }

    public void setCasts(ArrayList<Cast> Casts) {
        this.Casts = Casts;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(Title);
        parcel.writeString(Description);
        parcel.writeString(Poster);
        parcel.writeString(Time);
        parcel.writeString(Trailer);
        parcel.writeInt(Imdb);
        parcel.writeInt(Year);
        parcel.writeDouble(Price);
        parcel.writeStringList(Genre);
        parcel.writeTypedList(Casts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
