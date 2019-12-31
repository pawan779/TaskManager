package com.pawan.taskmanager.Url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {


    //for home
   // public static final String base_url="http://192.168.1.11:3000/";
    //for mobile
   // public static final String base_url="http://10.0.2.2:3000/";

    public static final String base_url="http://172.100.100.5:3000/";

    public static String token="";

    public static Retrofit getInstace(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit;


    }



}
