package com.example.i_rocket;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Agency implements Parcelable {
    private int id;
    private String url;
    private String name;
    private String type;

    protected Agency(Parcel in) {
        id = in.readInt();
        url = in.readString();
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<Agency> CREATOR = new Creator<Agency>() {
        @Override
        public Agency createFromParcel(Parcel in) {
            return new Agency(in);
        }

        @Override
        public Agency[] newArray(int size) {
            return new Agency[size];
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        dest.writeString(type);
    }
}
