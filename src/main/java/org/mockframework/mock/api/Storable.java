package org.mockframework.mock.api;

import org.aspectj.lang.JoinPoint;

/**
 * Created by deepWhite on 17/5/27.
 */
public interface Storable {
    /**
     * 生产mock数据
     * @param feature 特征值
     * @param resultData 结果数据
     * @return
     */
    public boolean productMockData(String feature, Object resultData, JoinPoint joinPoint);

    /**
     * 消费Mock数据
     * @param feature 特征值
     * @return
     */
    public Object consumerMockData(String feature, JoinPoint joinPoint);
}
