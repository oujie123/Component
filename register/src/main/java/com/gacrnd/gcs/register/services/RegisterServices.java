package com.gacrnd.gcs.register.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterServices {

    @POST("/ip/ipNew")
    @FormUrlEncoded
    Call<ResponseBody> get(@Field("ip") String ip, @Field("key") String key);
}
