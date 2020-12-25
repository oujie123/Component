package com.example.common.register.net;

import com.gacrnd.gcs.arouter_api.Call;

import java.io.IOException;

/**
 * @author Jack_Ou  created on 2020/12/25.
 */
public interface RegisterAddress extends Call {
    RegisterBean getRegisterBean(String key, String ip) throws IOException;
}
