package com.example.i_rocket;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;

public interface ApiService {
    @GET("2.2.0/expedition/?limit=10")
    Call<ExpeditionResponse> getExpedition(@Query("offset") int offset);
    @GET("2.2.0/expedition/")
    Call<ExpeditionResponse> getSearchEx(@Query("search") String search);

}
