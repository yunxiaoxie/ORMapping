package com.iti.rediscluster.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * for redis, trans object to byte[]
 */
public class SerializeUtil {
    /*
     * 序列化
     * */
    public static byte[] serizlize(Object object) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 反序列化
     * */
    public static Object deserialize(byte[] bytes) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais);) {
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json序列化
     * @param object
     * @return
     */
    public static String serizlizeJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * json反序列化-简单类型
     * @param jsonstr
     * @return
     */
    public static <T> T deserialize(String jsonstr, Class<T> klass) {
        T t = JSON.parseObject(jsonstr, klass);
        return t;
    }

    /**
     * json反序列化-复杂类型
     * @param jsonstr
     * @return
     */
    public static <T> T deserialize(String jsonstr, TypeReference<T> typeReference) {
        T t = JSON.parseObject(jsonstr, typeReference);
        return t;
    }
}
