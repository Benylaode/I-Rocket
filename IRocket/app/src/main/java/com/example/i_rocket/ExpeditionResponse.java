package com.example.i_rocket;

import java.util.List;


public class ExpeditionResponse {
    private int count;
    private String next;
    private String previous;
    private List<Expedition> results;

    // Getters and Setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Expedition> getResults() {
        return results;
    }

    public void setResults(List<Expedition> results) {
        this.results = results;
    }
}

