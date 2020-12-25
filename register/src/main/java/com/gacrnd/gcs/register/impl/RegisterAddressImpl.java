package com.gacrnd.gcs.register.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.arouter_annotations.ARouter;
import com.example.common.register.net.RegisterAddress;
import com.example.common.register.net.RegisterBean;
import com.gacrnd.gcs.register.services.RegisterServices;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * @author Jack_Ou  created on 2020/12/25.
 */
@ARouter(path = "/register/getRegisterBean")
public class RegisterAddressImpl implements RegisterAddress {

    private final static String BASE_URL = "http://apis.juhe.cn/";

    @Override
    public RegisterBean getRegisterBean(String key, String ip) throws IOException {
        // 一系列  Retrofit请求，得到结果后封装RegisterBean返回即可
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        RegisterServices host = retrofit.create(RegisterServices.class);

        // Retrofit GET同步请求
        Call<ResponseBody> call = host.get(ip, key);

        retrofit2.Response<ResponseBody> response = call.execute();
        if (response != null && response.body() != null) {
            JSONObject jsonObject = JSON.parseObject(response.body().string());
            RegisterBean orderBean = jsonObject.toJavaObject(RegisterBean.class);
            System.out.println("register订单组件中独有的网络请求功能：解析后结果 >>> " + orderBean.toString());
            return orderBean;
        }
        return new RegisterBean();
    }
}
