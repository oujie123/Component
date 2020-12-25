package com.gacrnd.gcs.register.model;

import com.example.common.register.user.BaseUser;

/**
 * @Author: Jack Ou
 * @CreateDate: 2020/12/24 23:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/24 23:10
 * @UpdateRemark: 更新说明
 */
public class UserInfo extends BaseUser {

    private String token;
    private int level;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "token='" + token + '\'' +
                ", level=" + level +
                "} " + super.toString();
    }
}

