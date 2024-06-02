package com.example.i_rocket;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class MissionPatch implements Parcelable {
    private int id;
    private String name;
    private int priority;
    private String image_url;
    private Agency agency;

    protected MissionPatch(Parcel in) {
        id = in.readInt();
        name = in.readString();
        priority = in.readInt();
        image_url = in.readString();
        agency = in.readParcelable(Agency.class.getClassLoader());
    }

    public static final Creator<MissionPatch> CREATOR = new Creator<MissionPatch>() {
        @Override
        public MissionPatch createFromParcel(Parcel in) {
            return new MissionPatch(in);
        }

        @Override
        public MissionPatch[] newArray(int size) {
            return new MissionPatch[size];
        }
    };

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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String imageUrl) {
        this.image_url = imageUrl;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(priority);
        dest.writeString(image_url);
        dest.writeParcelable(agency, flags);
    }
}