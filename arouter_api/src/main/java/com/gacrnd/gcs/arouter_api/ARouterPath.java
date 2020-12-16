package com.gacrnd.gcs.arouter_api;

import com.example.arouter_annotations.bean.RouterBean;

import java.util.Map;

/**
 * @author Jack_Ou  created on 2020/12/16.
 */
public interface ARouterPath {

    /**
     * 例如：order分组下有这些信息，personal分组下有这些信息
     *
     * @return key:"/order/Order_MainActivity"   或  "/personal/Personal_MainActivity"
     *         value: RouterBean==Order_MainActivity.class 或 RouterBean=Personal_MainActivity.class
     */
    Map<String, RouterBean> getPathMap();

}
