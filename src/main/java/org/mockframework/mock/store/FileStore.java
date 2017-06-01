package org.mockframework.mock.store;

import com.google.gson.Gson;
import org.mockframework.mock.api.Storable;
import org.mockframework.mock.util.DateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by deepWhite on 17/5/31.
 */
public class FileStore implements Storable{
    /**
     * 文件存储根路径
     */
    private static String FileStorePath = "/usr/local/var/mock/";

    /**
     * 最大写缓冲区长度
     */
    private static final int MAX_BUF_LENGTH = 1024*1024*2;

    /**
     * 写缓冲区
     */
    private StringBuffer writeBuffer;

    /**
     * 读缓冲区
     */
    private Map<String, Object> readMap;

    public FileStore() {
        writeBuffer = new StringBuffer();
        readMap = new HashMap<>();
    }

    /**
     * 存储路径生成
     * @return
     */
    private String getStoreFile(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        return className + "." + methodName + "_" + DateUtil.format(new Date(), "yyyy-MM-dd_HH") + ".txt";
    }

    @Override
    public boolean productMockData(String feature, Object resultData, JoinPoint joinPoint) {
        String storeFile = getStoreFile(joinPoint);
        File file = new File(FileStorePath + storeFile);
        String jsonData = new Gson().toJson(resultData) + "\n";
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            /**
             * 是否需要写入
             */
            if(!featureIsWrite(feature, file)) {
                return true;
            }
            //写入中文字符时解决中文乱码问题
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            //将java对象转换为json对象
            bufferedWriter.write(feature + "=" + jsonData);
            //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public Object consumerMockData(String feature, JoinPoint joinPoint) {
        String storeFile = getStoreFile(joinPoint);
        File file = new File(FileStorePath + storeFile);
        Object resultData;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String resStr="";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.indexOf(feature) >= 0) {
                    resStr = line.substring(feature.length() + 1);
                    break;
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
            /**
             * 获取方法返回类型
             */
            Signature signature =  joinPoint.getSignature();
            Class returnType = ((MethodSignature) signature).getReturnType();
            resultData = new Gson().fromJson(resStr, returnType);
        } catch (Exception e) {
            return null;
        }

        return resultData;
    }

    /**
     * 当前特征值是否存在
     * @return
     */
    private boolean featureIsWrite(String feature, File file) throws Exception{
        boolean isWrite = true;
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.indexOf(feature) >= 0) {
                isWrite = false;
                break;
            }
        }
        bufferedReader.close();
        inputStreamReader.close();
        fileInputStream.close();

        return isWrite;
    }
}
