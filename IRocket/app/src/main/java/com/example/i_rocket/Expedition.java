package com.example.i_rocket;
import java.util.List;

import java.util.List;

public class Expedition {
    private int id;
    private String url;
    private String name;
    private String start;
    private String end;
    private Spacestation spacestation;
    private List<Object> mission_patches; // Assuming mission_patches is an empty list in the JSON
    private List<Spacewalk> spacewalks;

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

    public Spacestation getSpacestation() {
        return spacestation;
    }

    public void setSpacestation(Spacestation spacestation) {
        this.spacestation = spacestation;
    }

    public List<Object> getMissionPatches() {
        return mission_patches;
    }

    public void setMissionPatches(List<Object> mission_patches) {
        this.mission_patches = mission_patches;
    }

    public List<Spacewalk> getSpacewalks() {
        return spacewalks;
    }

    public void setSpacewalks(List<Spacewalk> spacewalks) {
        this.spacewalks = spacewalks;
    }
}

