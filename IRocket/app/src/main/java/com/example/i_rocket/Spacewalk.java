package com.example.i_rocket;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Spacewalk implements Parcelable  {
    private int id;
    private String url;
    private String name;
    private String start;
    private String end;
    private String duration;
    private String location;

    protected Spacewalk(Parcel in) {
        id = in.readInt();
        url = in.readString();
        name = in.readString();
        start = in.readString();
        end = in.readString();
        duration = in.readString();
        location = in.readString();
    }

    public static final Creator<Spacewalk> CREATOR = new Creator<Spacewalk>() {
        @Override
        public Spacewalk createFromParcel(Parcel in) {
            return new Spacewalk(in);
        }

        @Override
        public Spacewalk[] newArray(int size) {
            return new Spacewalk[size];
        }
    };

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(duration);
        dest.writeString(location);
    }
}
