package com.ashishgaike.covidcasestracker.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ashishgaike.covidcasestracker.api.ApiInterface.*;

public class Apiutilities {
    private static Retrofit retrofit = null;
    public static ApiInterface getApiInerface(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
