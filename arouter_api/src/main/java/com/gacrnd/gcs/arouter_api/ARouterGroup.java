package com.gacrnd.gcs.arouter_api;

import java.util.Map;

/**
 * @author Jack_Ou  created on 2020/12/16.
 */
public interface ARouterGroup {

    /**
     * 例如：order分组下有这些信息，personal分组下有这些信息
     * 例如："order" --- ARouterPath的实现类 -->（APT生成出来的 ARouter$$Path$$order）
     *
     * @return  key:"order/app/personal"      value:系列的order组下面所有的（path---class）
     */
    Map<String, Class<? extends ARouterPath>> getGroupMap();
}
