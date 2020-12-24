package com.gacrnd.gcs.register.impl;

import com.example.arouter_annotations.ARouter;
import com.example.common.register.user.BaseUser;
import com.example.common.register.user.IUser;
import com.gacrnd.gcs.register.model.UserInfo;

/**
 * @Author: Jack Ou
 * @CreateDate: 2020/12/24 23:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/24 23:08
 * @UpdateRemark: 更新说明
 */
@ARouter(path = "/register/getUserInfo")
public class RegisterUserImpl implements IUser {
    @Override
    public BaseUser getUserInfo() {
        UserInfo user = new UserInfo();
        user.setToken("123141412");
        user.setLevel(100);
        user.setName("jack");
        user.setAccount("jackou");
        user.setPassword("123");
        user.setGender(1);
        return user;
    }
}
