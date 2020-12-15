package com.gacrnd.gcs.component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jack Ou
 * @CreateDate: 2020/12/15 0:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/15 0:20
 * @UpdateRemark: 更新说明
 */
public class Test {
    public static void main(String[] args) {
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            float temp = (float) Math.sin(2*Math.PI * i / 20);
            list.add(temp);
        }
        System.out.println(list.toString());
    }
}
