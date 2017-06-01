package org.mockframework.mock.store;

import org.mockframework.mock.api.Storable;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by deepWhite on 17/5/27.
 */
public class MemoryStore implements Storable{
    private Map<String, Object> dataSnapShot;

    public MemoryStore() {
        this.dataSnapShot = new HashMap<String, Object>();
    }

    @Override
    public boolean productMockData(String feature, Object resultData, JoinPoint joinPoint) {
        if (StringUtils.isEmpty(feature)) {
            return false;
        }
        dataSnapShot.put(feature, resultData);

        return true;
    }

    @Override
    public Object consumerMockData(String feature, JoinPoint joinPoint) {
        if (StringUtils.isEmpty(feature)) {
            return null;
        }
        return dataSnapShot.get(feature);
    }
}
