package com.gacrnd.gcs.arouter_api;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 跳转时 ，用于参数的传递
 */
public class BundleManager {

    // Intent传输  携带的值，保存到这里
    private Bundle bundle = new Bundle();

    private Call call;

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    // 对外界提供，可以携带参数的方法
    public BundleManager withString(@NonNull String key, @Nullable String value) {
        bundle.putString(key, value);
        return this; // 链式调用效果 模仿开源框架
    }

    public BundleManager withBoolean(@NonNull String key, @Nullable boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }

    public BundleManager withInt(@NonNull String key, @Nullable int value) {
        bundle.putInt(key, value);
        return this;
    }

    public BundleManager withBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    // 直接完成跳转
    public Object navigation(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return RouterManager.getInstance().navigation(context, this);
        }
        return null;
    }
}
