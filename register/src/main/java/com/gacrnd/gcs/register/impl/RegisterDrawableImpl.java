package com.gacrnd.gcs.register.impl;

import com.example.arouter_annotations.ARouter;
import com.example.common.register.drawable.RegisterDrawable;
import com.gacrnd.gcs.register.R;

/**
 * @Author: Jack Ou
 * @CreateDate: 2020/12/23 23:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/23 23:08
 * @UpdateRemark: 更新说明
 */
@ARouter(path = "/register/getDrawable")
public class RegisterDrawableImpl implements RegisterDrawable {
    @Override
    public int getDrawable() {
        return R.drawable.ic_brightness_5_black_24dp;
    }
}
