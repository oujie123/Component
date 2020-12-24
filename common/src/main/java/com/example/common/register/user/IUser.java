package com.example.common.register.user;

import com.gacrnd.gcs.arouter_api.Call;

/**
 * @Author: Jack Ou
 * @CreateDate: 2020/12/24 23:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/24 23:04
 * @UpdateRemark: 更新说明
 */
public interface IUser extends Call {

    BaseUser getUserInfo();
}
