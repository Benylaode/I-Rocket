package com.example.i_rocket;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Spacestation implements Parcelable {
    private int id;
    private String url;
    private String name;
    private Status status;
    private String orbit;
    private String image_url;

    protected Spacestation(Parcel in) {
        id = in.readInt();
        url = in.readString();
        name = in.readString();
        orbit = in.readString();
        image_url = in.readString();
    }
    protected Spacestation(String name, String image_url) {
        this.name = name;
        this.image_url = image_url;
    }

    public static final Creator<Spacestation> CREATOR = new Creator<Spacestation>() {
        @Override
        public Spacestation createFromParcel(Parcel in) {
            return new Spacestation(in);
        }

        @Override
        public Spacestation[] newArray(int size) {
            return new Spacestation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(orbit);
        dest.writeString(image_url);
    }

    public static class Status {
        private int id;
        private String name;

        // Getters and Setters
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
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getOrbit() {
        return orbit;
    }

    public void setOrbit(String orbit) {
        this.orbit = orbit;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }
}
