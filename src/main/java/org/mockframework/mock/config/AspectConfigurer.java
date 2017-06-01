package org.mockframework.mock.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by deepWhite on 17/5/31.
 * mock 切面配置器
 */
@Setter
@Getter
public class AspectConfigurer {
    /**
     * mock切换开关
     */
    private String mockSwitch;
    /**
     * mock数据存储类型
     */
    private String storeType;
}
